package com.space.munovaapi.order.controller;

import com.space.munovaapi.auth.exception.AuthException;
import com.space.munovaapi.core.config.ResponseApi;
import com.space.munovaapi.core.dto.PagingResponse;
import com.space.munovaapi.order.dto.CreateOrderRequest;
import com.space.munovaapi.order.dto.GetOrderDetailResponse;
import com.space.munovaapi.order.dto.OrderSummaryDto;
import com.space.munovaapi.order.dto.PaymentPrepareResponse;
import com.space.munovaapi.order.entity.Order;
import com.space.munovaapi.order.exception.OrderException;
import com.space.munovaapi.order.exception.OrderItemException;
import com.space.munovaapi.order.service.OrderService;
import com.space.munovaapi.payment.exception.PaymentException;
import com.space.munovaapi.product.application.exception.ProductDetailException;
import com.space.munovaapi.security.jwt.JwtHelper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 생성 후 결제에 필요한 응답 보내기
     */
    @PostMapping
    public ResponseApi<PaymentPrepareResponse> createOrder(
            @RequestBody CreateOrderRequest request,
            HttpServletResponse response
    ) {
        Long memberId = JwtHelper.getMemberId();

        try {
            Order order = orderService.createOrder(request, memberId);
            orderService.saveOrderLog(order);
            PaymentPrepareResponse paymentResponse = PaymentPrepareResponse.from(order);
            return ResponseApi.created(response, paymentResponse);
        } catch (AuthException | OrderException | OrderItemException | ProductDetailException e) {
            return ResponseApi.nok(e.getStatusCode(), e.getCode(), e.getMessage());
        }
    }

    @GetMapping
    public ResponseApi<PagingResponse<OrderSummaryDto>> getOrders(@RequestParam(value = "page", defaultValue = "0") int page) {
        Long memberId = JwtHelper.getMemberId();
        if (page < 0) page = 0;

        PagingResponse<OrderSummaryDto> response = orderService.getOrderList(page, memberId);

        return ResponseApi.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseApi<?> getOrderDetail(@PathVariable("orderId") Long orderId) {
        Long memberId = JwtHelper.getMemberId();

        try {
            GetOrderDetailResponse response = orderService.getOrderDetail(orderId, memberId);
            return ResponseApi.ok(response);
        } catch (OrderException | AuthException | PaymentException e) {
            return ResponseApi.nok(e.getStatusCode(), e.getCode(), e.getMessage());
        }

    }
}
