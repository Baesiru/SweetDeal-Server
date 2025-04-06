package com.baesiru.store.domain.store.repository;

import com.baesiru.store.domain.store.repository.enums.StoreCategory;
import com.baesiru.store.domain.store.repository.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String businessNumber;
    @Enumerated(value = EnumType.STRING)
    private StoreCategory category;
    @Enumerated(value = EnumType.STRING)
    private StoreStatus status;
    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;
    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;
    private LocalDateTime requestedAt;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private Long userId;
}
