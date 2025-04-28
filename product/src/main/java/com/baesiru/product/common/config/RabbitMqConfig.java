package com.baesiru.product.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    @Value("${spring.rabbitmq.product.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.product.update.queue}")
    private String updateQueue;
    @Value("${spring.rabbitmq.product.update.routing-key}")
    private String updateRoutingKey;
    @Value("${spring.rabbitmq.product.cancel.queue}")
    private String cancelQueue;
    @Value("${spring.rabbitmq.product.cancel.routing-key}")
    private String cancelRoutingKey;

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
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange productExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue updateQueue() {
        return new Queue(updateQueue);
    }

    @Bean
    public Binding updateBinding() {
        return BindingBuilder.bind(updateQueue())
                .to(productExchange())
                .with(updateRoutingKey);
    }

    @Bean
    public Queue cancelQueue() {
        return new Queue(cancelQueue);
    }

    @Bean
    public Binding cancelBinding() {
        return BindingBuilder.bind(cancelQueue())
                .to(productExchange())
                .with(cancelRoutingKey);
    }

}
