package com.space.munovaapi.order.service.processor;

import com.space.munovaapi.order.dto.CreateOrderRequest;
import com.space.munovaapi.order.entity.Order;

public interface OrderAmountProcessor {
    void process(Order order, CreateOrderRequest request, long totalAmount);
}
