package com.example.amqptp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApp {

    private final RabbitTemplate rabbitTemplate;

    public ConsumerApp(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }

    @RabbitListener(queues = "#{anonymousQueue.name}")
    public void handleRequest(String input) {
        System.out.println("Message reçu : " + input);
        String response = "Réponse à : " + input;
        rabbitTemplate.convertAndSend(System.getenv().getOrDefault("RESP_QUEUE", "responses"), response);
    }
}
