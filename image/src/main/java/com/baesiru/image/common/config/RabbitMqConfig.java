package com.baesiru.image.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.image.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.image.assign.queue}")
    private String assignQueue;
    @Value("${spring.rabbitmq.image.assign.routing-key}")
    private String assignRoutingKey;
    @Value("${spring.rabbitmq.image.update.queue}")
    private String updateQueue;
    @Value("${spring.rabbitmq.image.update.routing-key}")
    private String updateRoutingKey;


    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange imageExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue assignQueue() {
        return new Queue(assignQueue);
    }

    @Bean
    public Queue updateQueue() {
        return new Queue(updateQueue);
    }

    @Bean
    public Binding assignBinding() {
        return BindingBuilder.bind(assignQueue())
                .to(imageExchange())
                .with(assignRoutingKey);
    }

    @Bean
    public Binding updateBinding() {
        return BindingBuilder.bind(updateQueue())
                .to(imageExchange())
                .with(updateRoutingKey);
    }

}
