package com.pplbo.ecommerce.paymentservice.repository;

import com.example.paymentservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
