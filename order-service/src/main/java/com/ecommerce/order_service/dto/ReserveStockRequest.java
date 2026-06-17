package com.ecommerce.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReserveStockRequest {

    private Long productId;
    private Integer quantity;
    private String idempotencyKey;
}
