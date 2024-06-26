package me.qunqun.user.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayMqConfig
{
	public final static String PAY_DIRECT_QUEUE = "PayDirectQueue";
	public final static String PAY_DIRECT_EXCHANGE = "PayDirectExchange";
	public final static String PAY_DIRECT_ROUTING_KEY = "PayDirectRoutingKey";
	
	@Bean
	public Queue PayDirectQueue()
	{
		return new Queue(PAY_DIRECT_QUEUE,true);
	}
	
	@Bean
	public DirectExchange PayDirectExchange()
	{
		return new DirectExchange(PAY_DIRECT_EXCHANGE,true,false);
	}
	
	@Bean
	public Binding bindingPayDirect()
	{
		return BindingBuilder.bind(PayDirectQueue()).to(PayDirectExchange()).with(PAY_DIRECT_ROUTING_KEY);
	}
	
	
	
}
