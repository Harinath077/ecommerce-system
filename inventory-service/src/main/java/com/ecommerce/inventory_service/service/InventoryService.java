package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.dto.InventoryRequest;
import com.ecommerce.inventory_service.dto.InventoryResponse;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest request);

    InventoryResponse getInventoryByProductId(Long productId);

    InventoryResponse updateStock(Long productId, Integer quantity);
}
