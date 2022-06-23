package ru.itis.lanya.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlertConfig {

    private final String queueName = "alertQueue";

    @Autowired
    private TopicExchange topicExchange;

    @Bean
    public Queue alertQueue(){
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding alertBinding(){
        return BindingBuilder.bind(alertQueue()).to(topicExchange).with("alert");
    }
}
