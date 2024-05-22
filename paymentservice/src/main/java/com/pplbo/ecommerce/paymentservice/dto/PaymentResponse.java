package com.pplbo.ecommerce.paymentservice.dto;

public class PaymentResponse {
    private String paymentId;
    private String orderId;
    private String status;
    private double amount;
    private String currency;
    private PaymentMethod paymentMethod;

    // Getters and Setters

    public static class PaymentMethod {
        private String type;
        private String last4;

        // Getters and Setters
    }
}
