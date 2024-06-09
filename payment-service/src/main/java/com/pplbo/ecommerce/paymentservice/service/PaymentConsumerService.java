package com.pplbo.ecommerce.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pplbo.ecommerce.paymentservice.dto.PaymentRequest;
import com.pplbo.ecommerce.paymentservice.dto.PaymentResponse;
import com.pplbo.ecommerce.paymentservice.event.ValidatePayment;
import java.util.Date;

@Service
public class PaymentConsumerService {

    private static final String TOPIC = "payment";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }

    public void sendPaymentEvent(ValidatePayment validatePayment) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String message = objectMapper.writeValueAsString(validatePayment);
            kafkaTemplate.send(TOPIC, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
