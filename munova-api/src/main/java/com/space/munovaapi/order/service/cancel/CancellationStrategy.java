package com.space.munovaapi.order.service.cancel;

import com.space.munovaapi.order.dto.OrderStatus;
import com.space.munovaapi.order.entity.OrderItem;

public interface CancellationStrategy {
    void validate(OrderStatus orderStatus);
    void updateOrderItemStatus(OrderItem orderItem);
}
