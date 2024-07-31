package com.example.ecommercesystem.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public TestProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        System.out.println("Sent message: " + message + " to topic: " + topic);
    }

    // Convenience method to send to default topic
    public void sendMessage(String message) {
        sendMessage("test_topic", message);
    }
}
