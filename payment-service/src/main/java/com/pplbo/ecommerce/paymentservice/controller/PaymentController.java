package com.pplbo.ecommerce.paymentservice.controller;

import com.pplbo.ecommerce.paymentservice.dto.PaymentRequest;
import com.pplbo.ecommerce.paymentservice.dto.PaymentResponse;
import com.pplbo.ecommerce.paymentservice.event.ValidatePayment;
import com.pplbo.ecommerce.paymentservice.model.Payment;
import com.pplbo.ecommerce.paymentservice.service.PaymentService;
import com.pplbo.ecommerce.paymentservice.service.PaymentProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentProducerService paymentProducerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentResponse> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable("id") Long id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment createPayment(@RequestBody Payment payment) {
        ValidatePayment validatePayment = new ValidatePayment(payment);
        paymentProducerService.sendPaymentEvent(validatePayment);
        return payment;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable("id") Long id,
            @RequestBody PaymentRequest paymentRequest) {
        Optional<PaymentResponse> existingPayment = paymentService.getPaymentById(id);

        if (existingPayment.isPresent()) {
            PaymentResponse updatedPayment = paymentService.updatePayment(id, paymentRequest);
            return ResponseEntity.ok(updatedPayment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletePayment(@PathVariable("id") Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
