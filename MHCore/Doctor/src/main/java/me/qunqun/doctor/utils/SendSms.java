package me.qunqun.doctor.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.log4j.Log4j2;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class SendSms {

    // 签名
    private static final String signName = "熙心健康体检";

    // 模板
    private static final String reportSMSTemplateCode = "SMS_468195539";
    private static final String tipSMSTemplateCode = "SMS_468320452";

    // 阿里云短信配置信息
    private static final String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
    private static final String accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");
    private static final String REGION_ID = "cn-shanghai";
    private static final String PRODUCT = "Dysmsapi";
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";


    public static boolean sendSMSReportComplete(String mobile, String userName, String orderId) {
        try {
            IClientProfile profile = DefaultProfile.getProfile(REGION_ID, accessKeyId, accessKeySecret);

            DefaultProfile.addEndpoint(REGION_ID, REGION_ID, PRODUCT, DOMAIN);

            IAcsClient acsClient = new DefaultAcsClient(profile);

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

    public static boolean sendSMSTip(String mobile, String userName, String orderId, String hospital, String date) {
        try {
            IClientProfile profile = DefaultProfile.getProfile(REGION_ID, accessKeyId, accessKeySecret);

            DefaultProfile.addEndpoint(REGION_ID, REGION_ID, PRODUCT, DOMAIN);

            IAcsClient acsClient = new DefaultAcsClient(profile);

            SendSmsRequest request = new SendSmsRequest();

            request.setMethod(MethodType.POST);

            // 手机号可以单个也可以多个（多个用逗号隔开，如：15*******13,13*******27,17*******56）
            request.setPhoneNumbers(mobile);
            request.setSignName(signName);
            request.setTemplateCode(tipSMSTemplateCode);

            request.setTemplateParam("{\"name\":\""+ userName +"\",\"orderId\":\""+ orderId +"\",\"date\":\""+ date +"\",\"hosipital\":\""+ hospital +"\"}");


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
        if (str != null && str.size() > 0) {
            for (String s : str) {
                newS.append(s).append(",");
            }
        }
        if (newS.length() > 0)
            newS.deleteCharAt(newS.length() - 1);// 删除最后一个多余的逗号
        return newS.toString();
    }

//    public static void main(String[] args) {
////        多发
//        List<String> mobiles = new ArrayList<>();
//        mobiles.add("13414157968");
////        mobiles.add("13*******27");
////        mobiles.add("17*******56");
//        String s= getSplitString(mobiles);
////        System.out.println(SendSms.sendSMSReportComplete(s, "谢先生",  "20210707123456789"));
//        System.out.println(SendSms.sendSMSTip(s, "谢先生",  "100569001", "上海市第一人民医院", "2021-07-07"));
////         单发
////        System.out.println(SendSmsUtil.sendSMS("177******56", "赵四",  -2000L));
//    }

}


