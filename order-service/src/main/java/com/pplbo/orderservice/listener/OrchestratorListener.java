// package com.pplbo.orderservice.listener;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Component;

// import com.pplbo.orderservice.service.OrderService;

// @Component
// public class OrchestratorListener {

//     @Autowired
//     private OrderService orderService;

//     @KafkaListener(topics = "orderReply", groupId = "group_id")
//     public void handleReply(String message) {
//         // Tangani balasan dari service lain
//         orderService.handleReply(message);
//     }
// }
