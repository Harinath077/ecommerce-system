package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.dto.AddInventoryRequest;
import com.ecommerce.inventory_service.dto.InventoryResponse;
import com.ecommerce.inventory_service.dto.ReserveInventoryRequest;
import com.ecommerce.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> addInventory(
            @Valid @RequestBody AddInventoryRequest request) {

        InventoryResponse response = inventoryService.addInventory(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getInventory(
            @PathVariable Long productId) {

        InventoryResponse response = inventoryService.getInventory(productId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reserve")
    public ResponseEntity<InventoryResponse> reserveInventory(
            @Valid @RequestBody ReserveInventoryRequest request) {

        InventoryResponse response = inventoryService.reserveInventory(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/release")
    public ResponseEntity<InventoryResponse> releaseInventory(
            @Valid @RequestBody ReserveInventoryRequest request) {

        InventoryResponse response = inventoryService.releaseInventory(request);
        return ResponseEntity.ok(response);
    }
}
