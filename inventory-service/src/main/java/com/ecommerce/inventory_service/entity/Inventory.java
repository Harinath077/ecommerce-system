package com.ecommerce.inventory_service.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Integer availableQuantity;

    private Integer reservedQuantity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
