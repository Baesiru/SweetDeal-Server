package com.baesiru.image.domain.image.controller;

import com.baesiru.global.api.Api;
import com.baesiru.image.common.response.MessageResponse;
import com.baesiru.image.domain.image.business.ImageBusiness;
import com.baesiru.image.domain.image.controller.model.ImageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageApiController {
    @Autowired
    private ImageBusiness imageBusiness;

    @PostMapping("/upload")
    public Api<MessageResponse> upload(ImageRequest imageRequest) {
        MessageResponse response = imageBusiness.upload(imageRequest);
        return Api.OK(response);
    }
}
