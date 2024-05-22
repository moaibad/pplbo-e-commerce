package com.pplbo.ecommerce.paymentservice.dto;

import java.util.Date;

public class PaymentDTO {
    private Long paymentId;
    private Long orderId;
    private String paymentMethod;
    private Date paymentDate;
    private String paymentStatus;
    private Double paymentAmount;

    // Default Constructor
    public PaymentDTO() {
    }

    // Constructor with parameters
            
    public PaymentDTO(Long paymentId, Long orderId, String paymentMethod, Date paymentDate, String paymentStatus, Double paymentAmount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.paymentAmount = paymentAmount;
    }

    // Getters
    public Long getPaymentId() {
        return paymentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    // Setters
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
