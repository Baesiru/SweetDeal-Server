package com.baesiru.store.domain.store.controller.model.response;

import com.baesiru.store.domain.store.repository.enums.StoreCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStoreResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String businessNumber;
    private StoreCategory category;
}
