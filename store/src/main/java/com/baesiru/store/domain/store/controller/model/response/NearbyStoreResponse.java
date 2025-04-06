package com.baesiru.store.domain.store.controller.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NearbyStoreResponse {
    private Long id;
    private String latitude;
    private String longitude;
}
