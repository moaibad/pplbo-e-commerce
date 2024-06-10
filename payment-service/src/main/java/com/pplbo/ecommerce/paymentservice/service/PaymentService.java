package com.pplbo.ecommerce.paymentservice.service;

import com.pplbo.ecommerce.paymentservice.dto.PaymentForm;
import com.pplbo.ecommerce.paymentservice.dto.PaymentRequest;
import com.pplbo.ecommerce.paymentservice.dto.PaymentResponse;
import com.pplbo.ecommerce.paymentservice.event.ValidatePayment;
import com.pplbo.ecommerce.paymentservice.model.Payment;
import com.pplbo.ecommerce.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;

@Service
public class PaymentService {

        @Autowired
        private PaymentRepository paymentRepository;
        private static final String TOPIC = "payment";

        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;
        private final ObjectMapper objectMapper = new ObjectMapper();

        public void sendMessage(String message) {
                kafkaTemplate.send(TOPIC, message);
        }

        public void sendPaymentEvent(ValidatePayment validatePayment) {
                try {
                        String message = objectMapper.writeValueAsString(validatePayment);
                        kafkaTemplate.send(TOPIC, message);
                        System.out.println("Produced message: " + message);
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        @Transactional(readOnly = true)
        public List<PaymentResponse> getAllPayments() {
                return paymentRepository.findAll().stream()
                                .map(this::toPaymentResponse)
                                .collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
        public Optional<PaymentResponse> getPaymentById(Long paymentId) {
                return paymentRepository.findById(paymentId)
                                .map(this::toPaymentResponse);
        }

        @Transactional
        public PaymentResponse createPayment(PaymentRequest paymentRequest) {
                Payment payment = toPayment(paymentRequest);
                payment = checkPayment(payment);
                payment = makePayment(payment);
                sendPaymentEvent(new ValidatePayment(payment));
                return toPaymentResponse(payment);
        }

        @Transactional
        public PaymentResponse updatePayment(Long paymentId, PaymentRequest paymentRequest) {
                Payment payment = toPayment(paymentRequest);
                payment.setPaymentId(paymentId);
                return toPaymentResponse(paymentRepository.save(payment));
        }

        @Transactional
        public void deletePayment(Long paymentId) {
                paymentRepository.deleteById(paymentId);
        }

        private Payment toPayment(PaymentRequest paymentRequest) {
                return Payment.builder()
                                .paymentId(paymentRequest.paymentId())
                                .orderId(paymentRequest.orderId())
                                .paymentMethod(paymentRequest.paymentMethod())
                                .paymentDate(paymentRequest.paymentDate())
                                .paymentStatus(paymentRequest.paymentStatus())
                                .paymentAmount(paymentRequest.paymentAmount())
                                .build();
        }

        private PaymentResponse toPaymentResponse(Payment payment) {
                return new PaymentResponse(
                                payment.getPaymentId(),
                                payment.getOrderId(),
                                payment.getPaymentMethod(),
                                payment.getPaymentAmount(),
                                payment.getPaymentStatus(),
                                payment.getPaymentDate());
        }

        public Payment checkPayment(Payment payment) {
                if (payment.getPaymentAmount() > 500) {
                        payment.setPaymentStatus("PAYMENT_SUCCESS");
                } else {
                        payment.setPaymentStatus("PAYMENT_FAILED");
                }
                return payment;
        }

        public Payment makePayment(Payment payment) {
                switch (payment.getPaymentMethod()) {
                        case "CREDIT_CARD":
                        case "DEBIT_CARD":
                                // Logic for credit/debit card payments
                                break;
                        case "DIGITAL_WALLET":
                                // Logic for digital wallet payments
                                break;
                        case "BANK_TRANSFER":
                                // Logic for bank transfer payments
                                break;
                        default:
                                throw new IllegalArgumentException(
                                                "Unsupported payment method: " + payment.getPaymentMethod());
                }
                return paymentRepository.save(payment);
        }

        @Transactional(readOnly = true)
        public PaymentForm generatePaymentForm(Long orderId) {
                // Fetch the payment method based on orderId
                Optional<Payment> paymentOpt = paymentRepository.findByOrderId(orderId);
                String paymentMethod = paymentOpt.map(Payment::getPaymentMethod).orElse(null);

                // In a real application, you would fetch these details from the order service
                Double orderAmount = 100.0; // Dummy value
                Date orderDate = new Date(); // Current date

                return new PaymentForm(orderId, orderAmount, orderDate, paymentMethod);
        }
}
