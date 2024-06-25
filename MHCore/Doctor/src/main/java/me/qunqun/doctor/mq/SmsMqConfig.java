package me.qunqun.doctor.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsMqConfig {

    public static final String SMSMQ_DIRECT_QUEUE = "SmsMqQueue";
    public static final String SMSMQ_DIRECT_EXCHANGE = "SmsMqExchange";
    public static final String SMSMQ_DIRECT_ROUTING_KEY = "SmsMqRouting";


    @Bean
    public Queue SmsMqQueue()
    {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。

        return new Queue(SMSMQ_DIRECT_QUEUE,true);
    }

    @Bean
    public DirectExchange SmsMqExchange()
    {
        // durable:是否持久化,默认是false,持久化交换机：会被存储在磁盘上，当消息代理重启时仍然存在
        // autoDelete:当所有绑定队列都不在使用时，是否自动删除交换机
        return new DirectExchange(SMSMQ_DIRECT_EXCHANGE,true,false);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：DefaultDirectRouting
    @Bean
    public Binding bindingSmsMq()
    {
        return BindingBuilder.
                bind(SmsMqQueue())
                .to(SmsMqExchange())
                .with(SMSMQ_DIRECT_ROUTING_KEY);
    }
}
