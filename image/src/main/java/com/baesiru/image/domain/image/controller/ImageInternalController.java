package com.baesiru.image.domain.image.controller;

import com.baesiru.image.common.response.MessageResponse;
import com.baesiru.image.domain.image.business.ImageBusiness;
import com.baesiru.image.domain.image.controller.model.request.AssignImageRequest;
import com.baesiru.image.domain.image.controller.model.request.ImagesRequest;
import com.baesiru.image.domain.image.controller.model.response.ImagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
public class ImageInternalController {
    @Autowired
    private ImageBusiness imageBusiness;

    @PostMapping("/images")
    public ResponseEntity<?> getImages(@RequestBody ImagesRequest imagesRequest) {
        ImagesResponse response = imageBusiness.getImages(imagesRequest);
        return ResponseEntity.ok(response);
    }

    /*
    RabbitMQ 도입으로 인한 삭제 예정
    @PostMapping("/assignimages")
    public ResponseEntity<MessageResponse> assignImages(@RequestBody AssignImageRequest assignImageRequest) {
        MessageResponse response = imageBusiness.assignImages(assignImageRequest);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/updateimages")
    public ResponseEntity<MessageResponse> updateImages(@RequestBody AssignImageRequest assignImageRequest) {
        MessageResponse response = imageBusiness.updateImage(assignImageRequest);
        return ResponseEntity.ok(response);
    }*/

}
