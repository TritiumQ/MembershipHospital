package me.qunqun.doctor.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class SmsUtil {
    // 签名
    @Value("${aliyun.sms.sign-name}")
    private String signName = "熙心健康体检";

    // 模板
    @Value("${aliyun.sms.template-code.report}")
    private String reportSMSTemplateCode = "SMS_468195539";
    @Value("${aliyun.sms.template-code.tip}")
    private String tipSMSTemplateCode = "SMS_468320452";

    // 阿里云短信配置信息
    @Value("${aliyun.sms.access-key-id-env}")
    private String accessKeyIdEnv;

    @Value("${aliyun.sms.access-key-secret-env}")
    private String accessKeySecretEnv;

    @Value("${aliyun.sms.REGION_ID}")
    private String REGION_ID;

    @Value("${aliyun.sms.PRODUCT}")
    private String PRODUCT;

    @Value("${aliyun.sms.DOMAIN}")
    private String DOMAIN;

    @Getter
    private IAcsClient acsClient;

    @PostConstruct
    public void init() {
        String accessKeyId = System.getenv(accessKeyIdEnv);
        String accessKeySecret = System.getenv(accessKeySecretEnv);
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, accessKeyId, accessKeySecret);
        this.acsClient = new DefaultAcsClient(profile);
    }


    public void sendSMSReportComplete(String mobile, String userName, String orderId) {
        try {
            DefaultProfile.addEndpoint(REGION_ID, REGION_ID, PRODUCT, DOMAIN);
            SendSmsRequest request = new SendSmsRequest();
            request.setMethod(MethodType.POST);
            // 手机号可以单个也可以多个（多个用逗号隔开，如：15*******13,13*******27,17*******56）
            request.setPhoneNumbers(mobile);
            request.setSignName(signName);
            request.setTemplateCode(reportSMSTemplateCode);
            request.setTemplateParam("{\"name\":\""+ userName +"\",\"orderId\":\""+ orderId +"\"}");
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if ((sendSmsResponse.getCode() != null) && (sendSmsResponse.getCode().equals("OK"))) {
                log.info("发送成功,code:" + sendSmsResponse.getCode());
            } else {
                log.info("发送失败,code:" + sendSmsResponse.getCode());
            }
        } catch (ClientException e) {
            log.error("发送失败,系统错误！", e);
        }
    }

    public void sendSMSTip(String mobile, String userName, String orderId, String hospital, String date) {
        try {
            DefaultProfile.addEndpoint(REGION_ID, REGION_ID, PRODUCT, DOMAIN);
            SendSmsRequest request = new SendSmsRequest();
            request.setMethod(MethodType.POST);
            // 手机号可以单个也可以多个（多个用逗号隔开，如：15*******13,13*******27,17*******56）
            request.setPhoneNumbers(mobile);
            request.setSignName(signName);
            request.setTemplateCode(tipSMSTemplateCode);
            hospital = removeNonChineseCharacters(hospital);

            request.setTemplateParam("{\"name\":\""+ userName +"\",\"orderId\":\""+ orderId +"\",\"date\":\""+ date +"\",\"hosipital\":\""+ hospital +"\"}");
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if ((sendSmsResponse.getCode() != null) && (sendSmsResponse.getCode().equals("OK"))) {
                log.info("发送成功,code:" + sendSmsResponse.getCode());
            } else {
                log.info("发送失败,code:" + sendSmsResponse.getCode());
            }
        } catch (ClientException e) {
            log.error("发送失败,系统错误！", e);
        }
    }

    public boolean sendSMS(String mobile, String templateCode, String templateParam) {
        try {
            DefaultProfile.addEndpoint(REGION_ID, REGION_ID, PRODUCT, DOMAIN);
            SendSmsRequest request = new SendSmsRequest();
            request.setMethod(MethodType.POST);
            // 手机号可以单个也可以多个（多个用逗号隔开，如：15*******13,13*******27,17*******56）
            request.setPhoneNumbers(mobile);
            request.setSignName(signName);
            request.setTemplateCode(templateCode);
            request.setTemplateParam(templateParam);

            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if ((sendSmsResponse.getCode() != null) && (sendSmsResponse.getCode().equals("OK"))) {
                log.info("发送成功,code:" + sendSmsResponse.getCode());
                return true;
            } else {
                log.info("发送失败,code:" + sendSmsResponse.getCode());
                return false;
            }
        } catch (ClientException e) {
            log.error("发送失败,系统错误！", e);
            return false;
        }
    }

    /**
     * 获取逗号分隔的拼接字符串
     *
     * @param str 用于拼接的字符串集合
     * @return String
     */
    public static String getSplitString(List<String> str) {
        StringBuilder newS = new StringBuilder();
        if (str != null && !str.isEmpty()) {
            for (String s : str) {
                newS.append(s).append(",");
            }
        }
        if (!newS.isEmpty())
            newS.deleteCharAt(newS.length() - 1);// 删除最后一个多余的逗号
        return newS.toString();
    }

    public static String removeNonChineseCharacters(String hospitalName) {
        return hospitalName.replaceAll("[^\u4e00-\u9fa5]", "");
    }


}


