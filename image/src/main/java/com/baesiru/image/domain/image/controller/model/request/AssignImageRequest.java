package com.baesiru.image.domain.image.controller.model.request;

import com.baesiru.image.domain.image.repository.enums.ImageKind;
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
