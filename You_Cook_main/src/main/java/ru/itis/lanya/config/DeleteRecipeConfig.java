package ru.itis.lanya.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteRecipeConfig {

    private final String queueName = "deleteRecipeQueue";

    @Autowired
    private TopicExchange topicExchange;

    @Bean
    public Queue deleteRecipeQueue(){
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding deleteRecipeBinding(){
        return BindingBuilder.bind(deleteRecipeQueue()).to(topicExchange).with("delete.recipe");
    }
}
