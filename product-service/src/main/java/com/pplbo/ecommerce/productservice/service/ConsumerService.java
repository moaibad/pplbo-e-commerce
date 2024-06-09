package com.pplbo.ecommerce.productservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.pplbo.ecommerce.productservice.event.OrderCreatedEvent;

import com.pplbo.ecommerce.productservice.dto.dtoorder.OrderLineItemResponse;
import com.pplbo.ecommerce.productservice.service.InventoryService;

import jakarta.persistence.criteria.CriteriaBuilder.In;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ConsumerService {
    private final InventoryService inventoryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // @KafkaListener(topics = "coba", groupId = "group_id")
    // public void consume(String message) {
    //     try {
    //         OrderCreatedEvent event = objectMapper.readValue(message, OrderCreatedEvent.class);
    //         handleOrderCreatedEvent(event);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    // private void handleOrderCreatedEvent(OrderCreatedEvent event) {
    //     // Process the event
    //     System.out.println("Handling user created event for user: " + event.getUser().getName());
    // }

    @KafkaListener(topics = "orderEvent", groupId = "group_id")
    public void consumeOrderEvent(String message) {
        try {
            OrderCreatedEvent event = objectMapper.readValue(message, OrderCreatedEvent.class);
            handleOrderCreatedEventOrder(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleOrderCreatedEventOrder(OrderCreatedEvent event) {
        // Process the event
        // System.out.println("Handling user created event for user: " + event.getOrder());
        boolean instock = true;

        // List<OrderLineItemResponse> orderLineItems = event.getOrder().orderLineItems();
        // System.out.println("Handling order line item: " + orderLineItems);

        List<OrderLineItemResponse> orderLineItems = event.getOrder().orderLineItems();
        String orderStatus = event.getOrder().orderStatus();

        if (orderStatus.equals("Processing")) {
            for (OrderLineItemResponse orderLineItem : orderLineItems) {
                if(inventoryService.checkStok(orderLineItem.productId(), orderLineItem.quantity()) == false){
                    instock = false;
                    break;
                }
            }
        }

        if(instock){
            for (OrderLineItemResponse orderLineItem : orderLineItems) {
                inventoryService.decreaseInventory(orderLineItem.productId(), orderLineItem.quantity());
            }
        }
    }
}
