package com.pplbo.ecommerce.paymentservice.event;

import com.pplbo.ecommerce.paymentservice.model.UserPayment;
import com.pplbo.ecommerce.paymentservice.model.Transaction;

public class ValidatePayment {
    private UserPayment userPayment;
    private Transaction transaction;

    public ValidatePayment(UserPayment userPayment, Transaction transaction) {
        this.userPayment = userPayment;
        this.transaction = transaction;
    }

    public UserPayment getUserPayment() {
        return userPayment;
    }

    public void setUserPayment(UserPayment userPayment) {
        this.userPayment = userPayment;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
