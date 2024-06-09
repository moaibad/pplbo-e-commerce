package com.pplbo.ecommerce.productservice.event;

import com.pplbo.ecommerce.productservice.model.Product;
import com.pplbo.ecommerce.orderservice.model.Order;

public class OrderCreatedEvent {
    private User user;

    public UserCreatedEvent() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
