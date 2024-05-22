package com.pplbo.ecommerce.paymentservice.service;

import com.pplbo.ecommerce.paymentservice.dto.PaymentRequest;
import com.pplbo.ecommerce.paymentservice.dto.PaymentResponse;
import com.pplbo.ecommerce.paymentservice.model.Payment;
import com.pplbo.ecommerce.paymentservice.repository.PaymentRepository;
import com.pplbo.ecommerce.paymentservice.kafka.PaymentResponseProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentGatewayService paymentGatewayService;
    private final PaymentResponseProducer paymentResponseProducer;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentGatewayService paymentGatewayService,
            PaymentResponseProducer paymentResponseProducer) {
        this.paymentRepository = paymentRepository;
        this.paymentGatewayService = paymentGatewayService;
        this.paymentResponseProducer = paymentResponseProducer;
    }

    public PaymentResponse processPayment(PaymentRequest request) {
        // Validate and process payment with the payment gateway
        boolean isAuthorized = paymentGatewayService.authorize(request);

        // Create payment entity and save to database
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID().toString());
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setPaymentMethodType(request.getPaymentMethod().getType());
        payment.setPaymentMethodDetails(request.getPaymentMethod().getDetails().toString());
        payment.setCreatedAt(LocalDateTime.now());

        paymentRepository.save(payment);

        // Prepare and send payment response
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(payment.getId());
        response.setOrderId(payment.getOrderId());
        response.setStatus(isAuthorized ? "AUTHORIZED" : "DECLINED");
        response.setAmount(payment.getAmount());
        response.setCurrency(payment.getCurrency());
        response.setPaymentMethod(payment.getPaymentMethodType(), payment.getPaymentMethodDetails());

        paymentResponseProducer.sendPaymentResponse(response);

        return response;
    }

    public PaymentResponse getPaymentStatus(String id) {
        // Fetch payment details from the repository
        Payment payment = paymentRepository.findById(id).orElse(null);
        if (payment != null) {
            PaymentResponse response = new PaymentResponse();
            response.setPaymentId(payment.getId());
            response.setOrderId(payment.getOrderId());
            response.setStatus(payment.getStatus());
            response.setAmount(payment.getAmount());
            response.setCurrency(payment.getCurrency());
            response.setPaymentMethod(payment.getPaymentMethodType(), payment.getPaymentMethodDetails());
            return response;
        } else {
            return null; // or throw NotFoundException
        }
    }
}
