package com.ecommerce.order_service.entity;

import com.ecommerce.order_service.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long userId;

    private Long productId;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column( unique = true, nullable = false)
    private String idempotencyKey;

    @CreationTimestamp
    private LocalDateTime creatAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;
}
