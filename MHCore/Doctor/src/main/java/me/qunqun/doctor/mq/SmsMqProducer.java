package me.qunqun.doctor.mq;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = SmsMqConfig.SMSMQ_DIRECT_QUEUE)
public class SmsMqProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @RabbitHandler
    public void sendObject(SmsMqMessage message)
    {
        log.info("生产者发送Message消息:{}", message);
        rabbitTemplate.convertAndSend(SmsMqConfig.SMSMQ_DIRECT_EXCHANGE,SmsMqConfig.SMSMQ_DIRECT_ROUTING_KEY, message);
    }

    @RabbitHandler
    public void sendString(String message)
    {
        log.info("生产者发送String消息:{}", message);
        rabbitTemplate.convertAndSend(SmsMqConfig.SMSMQ_DIRECT_EXCHANGE,SmsMqConfig.SMSMQ_DIRECT_ROUTING_KEY, message);
    }
}
