package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.dto.AddInventoryRequest;
import com.ecommerce.inventory_service.dto.InventoryResponse;
import com.ecommerce.inventory_service.dto.ReserveInventoryRequest;
import com.ecommerce.inventory_service.entity.Inventory;
import com.ecommerce.inventory_service.exception.ProductNotFoundException;
import com.ecommerce.inventory_service.exception.InsufficientStockException;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class InventoryServiceImp implements InventoryService{

    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryResponse addInventory( AddInventoryRequest request ){

        Inventory inventory = Inventory.builder()
                .productId(request.getProductId())
                .availableQuantity(request.getQuantity())
                .reservedQuantity(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Inventory savedInventory = inventoryRepository.save(inventory);

        return mapToResponse(savedInventory);
    }

    @Override
    public InventoryResponse getInventory(Long productId){

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow( () ->
                        new ProductNotFoundException("Product not found with id : " + productId));
        return mapToResponse(inventory);
    }

    @Override
    public InventoryResponse reserveInventory(ReserveInventoryRequest request){

        Inventory inventory = inventoryRepository.findByProductId(request.getProductId())
                .orElseThrow( () ->
                        new ProductNotFoundException("Product not found with id: " + request.getProductId()));

        if( inventory.getAvailableQuantity() < request.getQuantity()){
            throw new InsufficientStockException("Insufficient stock for product id:" + request.getProductId());
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - request.getQuantity());

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() + request.getQuantity());

        inventory.setUpdatedAt( LocalDateTime.now());

        Inventory updateInventory = inventoryRepository.save(inventory);

        return mapToResponse(updateInventory);
    }

    @Override
    public InventoryResponse releaseInventory(ReserveInventoryRequest request) {

        Inventory inventory = inventoryRepository.findByProductId(request.getProductId())
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id: " + request.getProductId()));

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() + request.getQuantity());

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() - request.getQuantity());

        inventory.setUpdatedAt(LocalDateTime.now());

        Inventory updatedInventory = inventoryRepository.save(inventory);

        return mapToResponse(updatedInventory);
    }

    private InventoryResponse mapToResponse(Inventory inventory){

        return InventoryResponse.builder()
                .productId(inventory.getProductId())
                .availableQuantity(inventory.getAvailableQuantity())
                .reservedQuantity(inventory.getReservedQuantity())
                .build();
    }





}
