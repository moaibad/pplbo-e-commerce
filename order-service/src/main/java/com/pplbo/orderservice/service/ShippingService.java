package com.pplbo.orderservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShippingService {

  @Autowired
  private final ShippingRepository shippingRepository;

  public void createShipping(ShippingRequest shippingRequest) {
    Shipping shipping = new Shipping();
    shipping.setShippingName(shippingRequest.getShippingName());
    shipping.setShippingPrice(shippingRequest.getShippingPrice());
    shipping.setShippingStatus(shippingRequest.getShippingStatus());
    shipping.setShippingAddress(shippingRequest.getShippingAddress());

    shippingRepository.save(shipping);
  }

  public Optional<ShippingResponse> findById(Long id) {
    Optional<Shipping> shippingOptional = shippingRepository.findById(id);

    if (shippingOptional.isPresent()) {
      Shipping shipping = shippingOptional.get();
      ShippingResponse shippingResponse = new ShippingResponse(shipping.getShippingName(), 
        shipping.getShippingPrice(), shipping.getShippingStatus(), shipping.getShippingAddress());
      return Optional.of(shippingResponse);
    }
    else {
      return Optional.empty();
    }
  }
  
}
