package com.pplbo.ecommerce.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pplbo.ecommerce.paymentservice.event.ValidatePayment;

@Service
public class PaymentProducerService {

    private static final String TOPIC = "payment";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendPaymentEvent(ValidatePayment validatePayment) {
        try {
            // Convert ValidatePayment to JSON
            String message = objectMapper.writeValueAsString(validatePayment);

            // Send message to Kafka
            kafkaTemplate.send(TOPIC, message);
            System.out.println("Produced message: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
