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
        private String details;

        // Getters and Setters

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
}
