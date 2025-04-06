package com.baesiru.store.domain.store.controller.model.response;

import com.baesiru.store.domain.store.repository.enums.StoreCategory;
import com.baesiru.store.domain.store.repository.enums.StoreStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String businessNumber;
    private StoreCategory category;
    private StoreStatus status;
    private LocalDateTime requestedAt;
    private LocalDateTime registeredAt;
}
