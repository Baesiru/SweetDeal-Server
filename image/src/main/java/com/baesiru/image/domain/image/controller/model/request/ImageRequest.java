package com.baesiru.image.domain.image.controller.model.request;

import com.baesiru.image.domain.image.repository.enums.ImageKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequest {
    private MultipartFile file;
    private ImageKind kind;
}
