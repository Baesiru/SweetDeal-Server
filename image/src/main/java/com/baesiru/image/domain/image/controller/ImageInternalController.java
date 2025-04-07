package com.baesiru.image.domain.image.controller;

import com.baesiru.global.api.Api;
import com.baesiru.image.common.response.MessageResponse;
import com.baesiru.image.domain.image.business.ImageBusiness;
import com.baesiru.image.domain.image.controller.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal")
public class ImageInternalController {
    @Autowired
    private ImageBusiness imageBusiness;
    @PostMapping("/assignimages")
    public ResponseEntity<MessageResponse> assignImage(@RequestBody AssignImageRequest assignImageRequest) {
        MessageResponse response = imageBusiness.assignImage(assignImageRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/images")
    public ResponseEntity<?> getImages(@RequestBody ImagesRequest imagesRequest) {
        ImagesResponse response = imageBusiness.getImages(imagesRequest);
        return ResponseEntity.ok(response);
    }

}
