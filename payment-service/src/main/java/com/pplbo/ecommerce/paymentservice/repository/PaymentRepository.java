package com.pplbo.ecommerce.paymentservice.repository;

import com.pplbo.ecommerce.paymentservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
