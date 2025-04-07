package com.baesiru.store.domain.store.infra.client.model.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignImageRequest {
    private Long storeId;
    private Long productId;
    private List<String> serverNames;
    private ImageKind kind;
}
