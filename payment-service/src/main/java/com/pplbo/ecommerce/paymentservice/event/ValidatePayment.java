package com.pplbo.ecommerce.paymentservice.event;

import com.pplbo.ecommerce.paymentservice.model.Payment;

public class ValidatePayment {
    private Payment payment;

    public ValidatePayment(Payment payment) {
        this.payment = payment;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
