package com.pplbo.orderservice.service;

import com.pplbo.orderservice.dto.CustomerRequest;
import com.pplbo.orderservice.model.Customer;
import com.pplbo.orderservice.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

  @Autowired
  private final CustomerRepository customerRepository;
  
  public void createCustomer(CustomerRequest customerRequest) {
    Customer customer = new Customer();
    customer.setFirstName(customerRequest.firstName());
    customer.setLastName(customerRequest.lastName());

    customerRepository.save(customer);
  }
}
