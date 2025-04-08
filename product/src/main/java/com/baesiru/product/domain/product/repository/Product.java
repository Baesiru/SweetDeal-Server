package com.baesiru.product.domain.product.repository;

import com.baesiru.product.domain.product.repository.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long count;
    @Column(nullable = false)
    private Long originalPrice;
    @Column(nullable = false)
    private Long discountedPrice;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private Long storeId;
    private LocalDateTime expiredAt;
    private LocalDateTime registeredAt;
}
