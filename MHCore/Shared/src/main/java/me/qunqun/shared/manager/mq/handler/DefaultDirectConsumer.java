package me.qunqun.shared.manager.mq.handler;

import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.manager.mq.entity.ObjectMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = "DefaultDirectQueue")
public class DefaultDirectConsumer
{
	
	@RabbitHandler
	public void consumeObject(ObjectMessage message)
	{
		log.info("消费者收到Message消息:{}", message);
	}
	
	@RabbitHandler
	public void consumeString(String message)
	{
		log.info("消费者收到String消息:{}", message);
	}
}
