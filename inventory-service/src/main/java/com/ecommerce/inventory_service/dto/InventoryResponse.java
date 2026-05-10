package com.ecommerce.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {

    private Long inventoryId;

    private Long productId;

    private Integer availableQuantity;

    private Integer reservedQuantity;
}
