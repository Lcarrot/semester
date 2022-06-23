package ru.itis.lanya.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReplyConfig {

    private final String queueName = "replyQueue";

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private TopicExchange topicExchange;

    @Bean
    public Queue replyQueue(){
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding replyBinding(){
        return BindingBuilder.bind(replyQueue()).to(topicExchange).with("reply");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReplyAddress(queueName);
        rabbitTemplate.setExchange(topicExchange.getName());
        return rabbitTemplate;
    }

}
