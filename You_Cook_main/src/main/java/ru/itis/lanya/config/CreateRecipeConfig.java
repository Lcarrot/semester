package ru.itis.lanya.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateRecipeConfig {

    private final String queueName = "createRecipeQueue";

    @Autowired
    private TopicExchange topicExchange;

    @Bean
    public Queue createRecipeQueue(){
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding createRecipeBinding(){
        return BindingBuilder.bind(createRecipeQueue()).to(topicExchange).with("create.recipe");
    }
}
