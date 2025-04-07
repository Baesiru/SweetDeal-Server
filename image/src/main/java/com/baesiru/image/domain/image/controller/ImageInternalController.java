package com.baesiru.image.domain.image.controller;

import com.baesiru.global.api.Api;
import com.baesiru.image.common.response.MessageResponse;
import com.baesiru.image.domain.image.business.ImageBusiness;
import com.baesiru.image.domain.image.controller.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal")
public class ImageInternalController {
    @Autowired
    private ImageBusiness imageBusiness;
    @PostMapping("/assignimages")
    public Api<MessageResponse> assignImage(@RequestBody AssignImageRequest assignImageRequest) {
        MessageResponse response = imageBusiness.assignImage(assignImageRequest);
        return Api.OK(response);
    }

    @PostMapping("/images")
    public Api<?> getImages(@RequestBody ImagesRequest imagesRequest) {
        List<ImageResponse> response = imageBusiness.getImages(imagesRequest);
        return Api.OK(response);
    }

}
