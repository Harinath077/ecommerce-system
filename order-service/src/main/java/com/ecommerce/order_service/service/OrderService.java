package com.ecommerce.order_service.service;

import com.ecommerce.order_service.dto.CreateOrderRequest;
import com.ecommerce.order_service.dto.OrderResponse;

public interface OrderService {

    OrderResponse createOrder( CreateOrderRequest request);
    OrderResponse getOrder(Long orderID);

}
