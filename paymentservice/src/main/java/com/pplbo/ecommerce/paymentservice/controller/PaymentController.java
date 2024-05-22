package com.pplbo.ecommerce.paymentservice.controller;

import com.pplbo.ecommerce.paymentservice.dto.PaymentRequest;
import com.pplbo.ecommerce.paymentservice.dto.PaymentResponse;
import com.pplbo.ecommerce.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> initiatePayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentStatus(@PathVariable String id) {
        PaymentResponse response = paymentService.getPaymentStatus(id);
        return ResponseEntity.ok(response);
    }
}
