package me.qunqun.shared.manager.mq.handler;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.manager.mq.entity.ObjectMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultDirectProducer
{
	@Resource
	private RabbitTemplate rabbitTemplate;
	
	public void sendObject(ObjectMessage message)
	{
		log.info("生产者发送Message消息:{}", message);
		rabbitTemplate.convertAndSend("DefaultDirectExchange","DefaultDirectRouting", message);
	}
	
	public void sendString(String message)
	{
		log.info("生产者发送String消息:{}", message);
		rabbitTemplate.convertAndSend("DefaultDirectExchange","DefaultDirectRouting", message);
	}
}
