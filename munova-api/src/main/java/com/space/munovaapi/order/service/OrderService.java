package com.space.munovaapi.order.service;

import com.space.munovaapi.core.dto.PagingResponse;
import com.space.munovaapi.order.dto.*;
import com.space.munovaapi.order.entity.Order;

public interface OrderService {

    Order createOrder(CreateOrderRequest request, Long memberId);
    PagingResponse<OrderSummaryDto> getOrderList(int page, Long memberId);
    GetOrderDetailResponse getOrderDetail(Long orderId, Long memberId);
    void saveOrderLog(Order order);
}
