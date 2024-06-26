package me.qunqun.shared.manager.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import jakarta.annotation.Resource;
import me.qunqun.shared.entity.po.Order;
import me.qunqun.shared.manager.redis.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * 支付服务管理器
 */
@Service
public class PayManager
{
	private static final Logger log = LoggerFactory.getLogger(PayManager.class);
	@Value("${app.alipay.appId}")
	private String appId;
	@Value("${app.alipay.privateKey}")
	private String privateKey;
	@Value("${app.alipay.alipayPublicKey}")
	private String alipayPublicKey;
	
	private AlipayConfig config;
	
	private AlipayConfig getConfig()
	{
		if (config == null)
		{
			config = new AlipayConfig();
			config.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
			config.setAppId(appId);
			config.setPrivateKey(privateKey);
			config.setAlipayPublicKey(alipayPublicKey);
			config.setFormat("json");
			config.setCharset("utf-8");
			config.setSignType("RSA2");
		}
		return config;
	}
	
	
	@Resource
	private RedisManager redisManager;
	
	public String testPay() throws AlipayApiException
	{
		var client = new DefaultAlipayClient(getConfig());
		AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
		AlipayTradePagePayModel model = new AlipayTradePagePayModel();
		model.setOutTradeNo("12345");
		model.setTotalAmount("112");
		model.setSubject("测试商品");
		model.setProductCode("FAST_INSTANT_TRADE_PAY");
		model.setQrPayMode("4");
		model.setQrcodeWidth(200L);
		request.setBizModel(model);
		AlipayTradePagePayResponse response = client.pageExecute(request, "POST");
		// 如果需要返回GET请求，请使用
		// AlipayTradePagePayResponse response = client.pageExecute(request, "GET");
		return response.getBody();
	}
	
	/**
	 * 创建支付
	 * @return 支付二维码
	 */
	public String createOrQueryPay(Order order)
	{
		Assert.notNull(order, "订单不能为空");
		if(redisManager.getString(genPayId(order.getId())) != null)
		{
			return redisManager.getString("pay:" + order.getId());
		}
		try{
			var client = new DefaultAlipayClient(getConfig());
			
			AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
			
			AlipayTradePagePayModel model = new AlipayTradePagePayModel();
			model.setOutTradeNo(order.getId().toString());
			model.setTotalAmount(order.getPackageField().getPrice().toString());
			model.setSubject(order.getPackageField().getName());
			model.setProductCode("FAST_INSTANT_TRADE_PAY");
			model.setTimeoutExpress("6m");
			model.setQrPayMode("4");
			model.setQrcodeWidth(150L);
			request.setBizModel(model);
			
			AlipayTradePagePayResponse response = client.pageExecute(request, "POST");
			// 如果需要返回GET请求，请使用
			// AlipayTradePagePayResponse response = client.pageExecute(request, "GET");
			var qrcode = response.getBody();
			redisManager.setString("pay:" + order.getId(), qrcode, 6 * 60L);
			return qrcode;
		}
		catch (AlipayApiException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询支付状态
	 * @param orderId 订单ID
	 * @return -1: 其他错误;  0: 未支付;  1: 已支付;
	 */
	public Integer queryPayStatus(Integer orderId)
	{
		Assert.notNull(orderId, "订单ID不能为空");
		if(redisManager.getString(genPayId(orderId)) == null)
		{
			return -1;
		}
		try
		{
			var client = new DefaultAlipayClient(getConfig());
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
			AlipayTradeQueryModel model = new AlipayTradeQueryModel();
			model.setOutTradeNo(orderId.toString());
			request.setBizModel(model);
			AlipayTradeQueryResponse response = client.execute(request);
			// 如果需要返回GET请求，请使用
			// AlipayTradePagePayResponse response = client.pageExecute(request, "GET");
			if(response.isSuccess())
			{
				log.info("支付状态查询成功:{}", response.getBody());
				var payStatus = response.getCode();
				if("10000".equals(payStatus))
				{
					if("TRADE_SUCCESS".equals(response.getTradeStatus()))
					{
						redisManager.deleteKey(genPayId(orderId));
						return 1;
					}
					else
					{
						return 0;
					}
				}
				else
				{
					return -1;
				}
			}
			else
			{
				return -1;
			}
		}
		catch (AlipayApiException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	private static String genPayId(int orderId)
	{
		return "pay:" + orderId;
	}
}
