package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.dto.AddInventoryRequest;
import com.ecommerce.inventory_service.dto.InventoryResponse;
import com.ecommerce.inventory_service.dto.ReserveInventoryRequest;
import com.ecommerce.inventory_service.entity.Inventory;
import com.ecommerce.inventory_service.exception.InsufficientStockException;
import com.ecommerce.inventory_service.exception.ProductNotFoundException;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImpTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServiceImp inventoryService;

    @Test
    void shouldAddInventorySuccessfully() {
        AddInventoryRequest request = new AddInventoryRequest();
        request.setProductId(101L);
        request.setQuantity(50);

        Inventory savedInventory = Inventory.builder()
                .id(1L)
                .productId(101L)
                .availableQuantity(50)
                .reservedQuantity(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(inventoryRepository.save(any(Inventory.class))).thenReturn(savedInventory);

        InventoryResponse response = inventoryService.addInventory(request);

        assertNotNull(response);
        assertEquals(1L, response.getInventoryId());
        assertEquals(101L, response.getProductId());
        assertEquals(50, response.getAvailableQuantity());
        assertEquals(0, response.getReservedQuantity());
        verify(inventoryRepository).save(any(Inventory.class));
    }

    @Test
    void shouldGetInventorySuccessfully() {
        Inventory existingInventory = Inventory.builder()
                .id(1L)
                .productId(101L)
                .availableQuantity(50)
                .reservedQuantity(10)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(inventoryRepository.findByProductId(101L)).thenReturn(Optional.of(existingInventory));

        InventoryResponse response = inventoryService.getInventory(101L);

        assertNotNull(response);
        assertEquals(1L, response.getInventoryId());
        assertEquals(101L, response.getProductId());
        assertEquals(50, response.getAvailableQuantity());
        assertEquals(10, response.getReservedQuantity());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(inventoryRepository.findByProductId(999L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> inventoryService.getInventory(999L));
    }

    @Test
    void shouldReserveInventorySuccessfully() {
        ReserveInventoryRequest request = new ReserveInventoryRequest();
        request.setProductId(101L);
        request.setQuantity(15);

        Inventory existingInventory = Inventory.builder()
                .id(1L)
                .productId(101L)
                .availableQuantity(50)
                .reservedQuantity(10)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(inventoryRepository.findByProductId(101L)).thenReturn(Optional.of(existingInventory));
        when(inventoryRepository.save(any(Inventory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        InventoryResponse response = inventoryService.reserveInventory(request);

        assertNotNull(response);
        assertEquals(1L, response.getInventoryId());
        assertEquals(35, response.getAvailableQuantity());
        assertEquals(25, response.getReservedQuantity());
        verify(inventoryRepository).save(existingInventory);
    }

    @Test
    void shouldThrowExceptionWhenReservingWithInsufficientStock() {
        ReserveInventoryRequest request = new ReserveInventoryRequest();
        request.setProductId(101L);
        request.setQuantity(60);

        Inventory existingInventory = Inventory.builder()
                .id(1L)
                .productId(101L)
                .availableQuantity(50)
                .reservedQuantity(10)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(inventoryRepository.findByProductId(101L)).thenReturn(Optional.of(existingInventory));

        assertThrows(InsufficientStockException.class, () -> inventoryService.reserveInventory(request));
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void shouldThrowExceptionWhenReservingForNonExistentProduct() {
        ReserveInventoryRequest request = new ReserveInventoryRequest();
        request.setProductId(999L);
        request.setQuantity(10);

        when(inventoryRepository.findByProductId(999L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> inventoryService.reserveInventory(request));
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void shouldReleaseInventorySuccessfully() {
        ReserveInventoryRequest request = new ReserveInventoryRequest();
        request.setProductId(101L);
        request.setQuantity(10);

        Inventory existingInventory = Inventory.builder()
                .id(1L)
                .productId(101L)
                .availableQuantity(40)
                .reservedQuantity(15)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(inventoryRepository.findByProductId(101L)).thenReturn(Optional.of(existingInventory));
        when(inventoryRepository.save(any(Inventory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        InventoryResponse response = inventoryService.releaseInventory(request);

        assertNotNull(response);
        assertEquals(1L, response.getInventoryId());
        assertEquals(50, response.getAvailableQuantity());
        assertEquals(5, response.getReservedQuantity());
        verify(inventoryRepository).save(existingInventory);
    }

    @Test
    void shouldThrowExceptionWhenReleasingForNonExistentProduct() {
        ReserveInventoryRequest request = new ReserveInventoryRequest();
        request.setProductId(999L);
        request.setQuantity(10);

        when(inventoryRepository.findByProductId(999L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> inventoryService.releaseInventory(request));
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }
}
