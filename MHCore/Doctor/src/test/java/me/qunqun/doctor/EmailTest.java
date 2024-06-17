package me.qunqun.doctor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@SpringBootTest
public class EmailTest {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from = "work-email";

    @Test
    void emilaTest(){
        SimpleMailMessage message = new SimpleMailMessage();
        //1.发送邮件人
        message.setFrom(from);
        //2.收信人
        message.setTo("1656426916@qq.com");
        //3.标题
        message.setSubject("【测试】");
        message.setText("测试邮件");
        mailSender.send(message);
    }
}
