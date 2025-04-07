package com.baesiru.store.domain.store.controller.model.response;

import com.baesiru.store.domain.store.repository.enums.StoreCategory;
import com.baesiru.store.domain.store.repository.enums.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerStoreResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String businessNumber;
    private StoreCategory category;
    private StoreStatus status;
    private List<String> serverNames;
    private LocalDateTime requestedAt;
    private LocalDateTime registeredAt;
}
