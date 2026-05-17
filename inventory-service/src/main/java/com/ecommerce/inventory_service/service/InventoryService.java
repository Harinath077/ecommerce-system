package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.dto.AddInventoryRequest;
import com.ecommerce.inventory_service.dto.InventoryResponse;
import com.ecommerce.inventory_service.dto.ReserveInventoryRequest;

public interface InventoryService {

    InventoryResponse addInventory(AddInventoryRequest request);

    InventoryResponse getInventory(Long productId);

    InventoryResponse reserveInventory(ReserveInventoryRequest request);

    InventoryResponse releaseInventory(ReserveInventoryRequest request);

}
