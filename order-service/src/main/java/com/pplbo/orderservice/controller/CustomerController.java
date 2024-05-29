package com.pplbo.orderservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pplbo.orderservice.service.CustomerService;
import com.pplbo.orderservice.dto.CustomerRequest;


@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

  @Autowired
  private final CustomerService customerService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String createCustomer(@RequestBody CustomerRequest customerRequest) {
    customerService.createCustomer(customerRequest);
    return "Customer created successfully";
  }

}
