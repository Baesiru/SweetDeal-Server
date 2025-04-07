package com.baesiru.store.domain.store.infra.client.model.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagesRequest {
    private Long storeId;
    private Long productId;
    private ImageKind kind;
}
