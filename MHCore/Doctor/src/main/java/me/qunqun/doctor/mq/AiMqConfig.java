package me.qunqun.doctor.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiMqConfig {

    public static final String AIMQ_DIRECT_QUEUE = "AiMqQueue";
    public static final String AIMQ_DIRECT_EXCHANGE = "AiMqExchange";
    public static final String AIMQ_DIRECT_ROUTING_KEY = "AiMqRouting";

    @Bean
    public Queue AiMqQueue()
    {
        return new Queue(AIMQ_DIRECT_QUEUE,true);
    }

    @Bean
    public DirectExchange AiMqExchange()
    {
        return new DirectExchange(AIMQ_DIRECT_EXCHANGE,true,false);
    }

    @Bean
    public Binding bindingAiMq()
    {
        return BindingBuilder.
                bind(AiMqQueue())
                .to(AiMqExchange())
                .with(AIMQ_DIRECT_ROUTING_KEY);
    }

}
