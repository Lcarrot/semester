package ru.itis.lanya.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FavoritesConfig {

    private final String queueName = "favoritesQueue";

    @Autowired
    private TopicExchange topicExchange;

    @Bean
    public Queue favoritesQueue(){
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding favoritesBinding(){
        return BindingBuilder.bind(favoritesQueue()).to(topicExchange).with("favorites");
    }
}
