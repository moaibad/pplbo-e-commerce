package com.pplbo.ecommerce.paymentservice.service;

import com.pplbo.ecommerce.paymentservice.dto.TransactionRequest;
import com.pplbo.ecommerce.paymentservice.dto.TransactionResponse;
import com.pplbo.ecommerce.paymentservice.model.Transaction;
import com.pplbo.ecommerce.paymentservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.pplbo.ecommerce.paymentservice.model.UserPayment;
import com.pplbo.ecommerce.paymentservice.repository.UserPaymentRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserPaymentRepository userPaymentRepository;

    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::toTransactionResponse)
                .collect(Collectors.toList());
    }

    public Optional<TransactionResponse> getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .map(this::toTransactionResponse);
    }

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        // Fetch the UserPayment
        UserPayment userPayment = userPaymentRepository.findById(transactionRequest.paymentId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid paymentId: " + transactionRequest.paymentId()));

        // Check if the balance is sufficient
        double balance = Double.parseDouble(userPayment.getBalance());
        String transactionStatus;
        if (balance > transactionRequest.transactionAmount()) {
            transactionStatus = "PAYMENT_SUCCESS";
            balance -= transactionRequest.transactionAmount();
        } else {
            transactionStatus = "PAYMENT_FAILED";
        }

        // Update the UserPayment balance
        userPayment.setBalance(String.valueOf(balance));
        userPaymentRepository.save(userPayment);

        // Create the transaction
        Transaction transaction = toTransaction(transactionRequest);
        transaction.setTransactionStatus(transactionStatus);
        transaction = transactionRepository.save(transaction);

        return toTransactionResponse(transaction);
    }

    public TransactionResponse updateTransaction(Long transactionId, TransactionRequest transactionRequest) {
        Transaction transaction = toTransaction(transactionRequest);
        transaction.setTransactionId(transactionId);
        transaction = transactionRepository.save(transaction);
        return toTransactionResponse(transaction);
    }

    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    private Transaction toTransaction(TransactionRequest transactionRequest) {
        return Transaction.builder()
                .transactionId(transactionRequest.transactionId())
                .paymentId(transactionRequest.paymentId())
                .orderId(transactionRequest.orderId())
                .transactionDate(transactionRequest.transactionDate())
                .transactionAmount(transactionRequest.transactionAmount())
                .transactionStatus(transactionRequest.transactionStatus())
                .build();
    }

    private TransactionResponse toTransactionResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getTransactionId(),
                transaction.getPaymentId(),
                transaction.getOrderId(),
                transaction.getTransactionDate(),
                transaction.getTransactionAmount(),
                transaction.getTransactionStatus());
    }
}
