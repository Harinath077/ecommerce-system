package com.ecommerce.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {

    private Long inventoryId;

    private Long productId;

    private Integer availableQuantity;

    private Integer reservedQuantity;
}
