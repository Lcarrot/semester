package ru.itis.lanya.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegistrationAlertConfig {

    private final String queueName = "registrationAlertQueue";

    @Autowired
    private TopicExchange topicExchange;

    @Bean
    public Queue registrationAlertQueue(){
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding registrationAlertBinding(){
        return BindingBuilder.bind(registrationAlertQueue()).to(topicExchange).with("registration");
    }
}
