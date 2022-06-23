package ru.itis.lanya.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecipesConfig {

    private final String queueName = "recipesQueue";

    @Autowired
    private TopicExchange topicExchange;

    @Bean
    public Queue recipesQueue(){
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding recipesBinding(){
        return BindingBuilder.bind(recipesQueue()).to(topicExchange).with("recipes");
    }
}
