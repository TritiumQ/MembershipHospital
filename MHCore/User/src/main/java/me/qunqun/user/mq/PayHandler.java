package me.qunqun.user.mq;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.po.Order;
import me.qunqun.shared.manager.mq.entity.ObjectMessage;
import me.qunqun.shared.manager.sms.SmsManager;
import me.qunqun.user.entity.vo.OrderInfoVo;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = {PayMqConfig.PAY_DIRECT_QUEUE})
public class PayHandler
{
	@Resource
	private RabbitTemplate rabbitTemplate;
	
	@Resource
	private SmsManager smsManager;
	
	public void send(ObjectMessage message)
	{
		log.info("Pay队列生产者发送Message消息:{}", message);
		rabbitTemplate.convertAndSend("DefaultDirectExchange","DefaultDirectRouting", message);
	}
	
	@RabbitHandler
	public void consume(ObjectMessage message)
	{
		log.info("Pay队列消费者收到Message消息:{}", message);
		var order = (OrderInfoVo) message.getMessageData();
		var msg = "您的体检预约登记成功，" + "订单号:" + order.getId() + "，预约时间：" + order.getDate() + "，请您准时到达。";
		smsManager.sendCaptcha(order.getUserId(), msg);
	}
	

}
