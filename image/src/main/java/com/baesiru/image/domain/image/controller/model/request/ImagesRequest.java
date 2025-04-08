package com.baesiru.image.domain.image.controller.model.request;

import com.baesiru.image.domain.image.repository.enums.ImageKind;
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
