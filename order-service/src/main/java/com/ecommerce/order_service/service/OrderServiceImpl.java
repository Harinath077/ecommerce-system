package com.ecommerce.order_service.service;

import com.ecommerce.order_service.dto.CreateOrderRequest;
import com.ecommerce.order_service.dto.OrderResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.enums.OrderStatus;
import com.ecommerce.order_service.exception.DuplicateOrderException;
import com.ecommerce.order_service.exception.OrderNotFoundException;
import com.ecommerce.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    // Injection
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        log.info("Received create order request : {}", request);

        orderRepository.findByIdempotencyKey(request.getIdempotencyKey())
                .ifPresent(order -> {
                    log.warn("Duplicate order request detected");
                    throw new DuplicateOrderException("Duplicate order request");
                });

        Order order = Order.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .status(OrderStatus.CREATED)
                .idempotencyKey(request.getIdempotencyKey())
                .build();

        Order saveOrder = orderRepository.save(order);

        log.info("order created successfully with id : {}", saveOrder.getId());

        return new OrderResponse(
                saveOrder.getId(),
                saveOrder.getStatus().name()
        );
    }
    @Override
    public OrderResponse getOrder(Long orderId) {

        log.info("Fetching order with id : {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order not found with id : {}", orderId);
                    return new OrderNotFoundException("Order Not Found");
                });

        log.info("Order Fetched Successfully");

        return new OrderResponse(
                order.getId(),
                order.getStatus().name()
        );
    }

}
