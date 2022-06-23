package ru.itis.lanya.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecipeConfig {

    private final String queueName = "recipeQueue";

    @Autowired
    private TopicExchange topicExchange;

    @Bean
    public Queue recipeQueue(){
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding recipeBinding(){
        return BindingBuilder.bind(recipeQueue()).to(topicExchange).with("recipe");
    }
}
