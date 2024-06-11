// package com.pplbo.ecommerce.productservice.event;

// import com.pplbo.ecommerce.productservice.model.User;


// public class OrderCreatedEvent {
//     private User user;

//         // Konstruktor default
//     public OrderCreatedEvent() {}

//     public OrderCreatedEvent(User user) {
//         this.user = user;
//     }

//     public User getUser() {
//         return user;
//     }

//     public void setUser(User user) {
//         this.user = user;
//     }
// }

package com.pplbo.ecommerce.productservice.event;

import com.pplbo.ecommerce.productservice.dto.dtoorder.OrderResponse;
import com.pplbo.ecommerce.productservice.model.order.Order;

public class OrderCreatedEvent {
    private OrderResponse order;

    public OrderCreatedEvent() {
    }
    
    public OrderCreatedEvent(OrderResponse order) {
        this.order = order;
    }

    public OrderResponse getOrder() {
        return order;
    }

    public void setOrder(OrderResponse order) {
        this.order = order;
    }

}
