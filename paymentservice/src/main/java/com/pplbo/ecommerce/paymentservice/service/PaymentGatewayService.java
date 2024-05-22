package com.pplbo.ecommerce.paymentservice.service;

import com.pplbo.ecommerce.paymentservice.dto.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayService {
    
    public boolean authorize(PaymentRequest paymentRequest){
        
        return true;
    }
}