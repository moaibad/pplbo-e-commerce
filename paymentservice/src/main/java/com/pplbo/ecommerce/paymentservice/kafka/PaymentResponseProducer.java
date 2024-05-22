package com.pplbo.ecommerce.paymentservice.kafka;

import com.pplbo.ecommerce.paymentservice.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentResponseProducer {

    private static final String TOPIC = "payment-responses";

    private final KafkaTemplate<String, PaymentResponse> kafkaTemplate;

    @Autowired
    public PaymentResponseProducer(KafkaTemplate<String, PaymentResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentResponse(PaymentResponse response) {
        kafkaTemplate.send(TOPIC, response);
    }
}
