package com.ecommerce.order_service.client;

import com.ecommerce.order_service.dto.InventoryResponse;
import com.ecommerce.order_service.dto.ReserveInventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", url = "${inventory.service.url}")
public interface InventoryClient {

    @PostMapping("/inventory/reserve")
    ResponseEntity<InventoryResponse> reserveStock(@RequestBody ReserveInventoryRequest request);
}
