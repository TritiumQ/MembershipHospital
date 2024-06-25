package me.qunqun.doctor.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = SmsMqConfig.SMSMQ_DIRECT_QUEUE)
public class SmsMqConsumer {

}
