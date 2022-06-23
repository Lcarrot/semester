package ru.itis.lanya.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EditRecipeConfig {

    private final String queueName = "editRecipeQueue";

    @Autowired
    private TopicExchange topicExchange;

    @Bean
    public Queue editRecipeQueue(){
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding editRecipeBinding(){
        return BindingBuilder.bind(editRecipeQueue()).to(topicExchange).with("edit.recipe");
    }
}
