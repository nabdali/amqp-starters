package com.example.amqptp;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    public static final String EXCHANGE_NAME = "amq.fanout";

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue anonymousQueue() {
        return new AnonymousQueue(); // queue temporaire unique par instance
    }

    @Bean
    public Binding binding(FanoutExchange exchange, Queue anonymousQueue) {
        return BindingBuilder.bind(anonymousQueue).to(exchange);
    }
}