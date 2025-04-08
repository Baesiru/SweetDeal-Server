package com.baesiru.store.domain.store.service.model.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignImageRequest {
    private Long storeId;
    private Long productId;
    private List<String> serverNames;
    private ImageKind kind;
}
