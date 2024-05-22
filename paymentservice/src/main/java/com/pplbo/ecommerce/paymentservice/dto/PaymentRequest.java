package com.pplbo.ecommerce.paymentservice.dto;

public class PaymentRequest {
    private String orderId;
    private double amount;
    private String currency;
    private PaymentMethod paymentMethod;

    // Getters and Setters

    public static class PaymentMethod {
        private String type;
        private CardDetails details;

        // Getters and Setters

        public static class CardDetails {
            private String cardNumber;
            private String expiryDate;
            private String cvv;

            // Getters and Setters
        }
    }
}
