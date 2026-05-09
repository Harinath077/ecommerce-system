package com.ecommerce.order_service.service;

import com.ecommerce.order_service.dto.CreateOrderRequest;
import com.ecommerce.order_service.dto.OrderResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.enums.OrderStatus;
import com.ecommerce.order_service.exception.DuplicateOrderException;
import com.ecommerce.order_service.exception.OrderNotFoundException;
import com.ecommerce.order_service.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void shouldCreateOrderSuccessfully() {

        CreateOrderRequest request = new CreateOrderRequest();
        request.setUserId(1L);
        request.setProductId(100L);
        request.setQuantity(2);
        request.setIdempotencyKey("abc123");

        when(orderRepository.findByIdempotencyKey("abc123"))
                .thenReturn(Optional.empty());

        Order savedOrder = Order.builder()
                .Id(1L)
                .userId(1L)
                .productId(100L)
                .quantity(2)
                .status(OrderStatus.CREATED)
                .idempotencyKey("abc123")
                .build();

        when(orderRepository.save(any(Order.class)))
                .thenReturn(savedOrder);

        OrderResponse response = orderService.createOrder(request);

        assertEquals(1L, response.getOrderId());
        assertEquals("CREATED", response.getStatus());

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void shouldThrowExceptionForDuplicateOrder() {

        CreateOrderRequest request = new CreateOrderRequest();
        request.setUserId(1L);
        request.setProductId(100L);
        request.setQuantity(2);
        request.setIdempotencyKey("abc123");

        Order existingOrder = Order.builder()
                .Id(1L)
                .userId(1L)
                .productId(100L)
                .quantity(2)
                .status(OrderStatus.CREATED)
                .idempotencyKey("abc123")
                .build();

        when(orderRepository.findByIdempotencyKey("abc123"))
                .thenReturn(Optional.of(existingOrder));

        assertThrows(
                DuplicateOrderException.class,
                () -> orderService.createOrder(request)
        );

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void shouldGetOrderSuccessfully() {
        Order existingOrder = Order.builder()
                .Id(1L)
                .userId(1L)
                .productId(100L)
                .quantity(2)
                .status(OrderStatus.CREATED)
                .idempotencyKey("abc123")
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));

        OrderResponse response = orderService.getOrder(1L);

        assertEquals(1L, response.getOrderId());
        assertEquals("CREATED", response.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                OrderNotFoundException.class,
                () -> orderService.getOrder(1L)
        );
    }
}
