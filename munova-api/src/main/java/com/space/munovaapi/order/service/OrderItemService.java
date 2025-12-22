package com.space.munovaapi.order.service;

import com.space.munovaapi.order.dto.CancelOrderItemRequest;
import com.space.munovaapi.order.dto.OrderItemRequest;
import com.space.munovaapi.order.entity.Order;
import com.space.munovaapi.order.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> deductStockAndCreateOrderItems(List<OrderItemRequest> orderItems, Order order);
    void cancelOrderItem(Long orderItemId, CancelOrderItemRequest request, Long memberId);
}
