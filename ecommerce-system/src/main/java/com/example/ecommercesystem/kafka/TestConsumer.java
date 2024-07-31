package com.example.ecommercesystem.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TestConsumer {

    @KafkaListener(topics = "test_topic", groupId = "group1")
    public void consume(String message) {
        System.out.println("Received test_topic Message: " + message);
        // Add order processing logic here
    }
}

