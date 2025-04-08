package com.baesiru.store.domain.store.service;

import com.baesiru.store.domain.store.service.model.image.AssignImageRequest;
import com.baesiru.store.domain.store.service.model.image.ImagesRequest;
import com.baesiru.store.domain.store.service.model.image.ImagesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "sweetdeal-images", path = "/internal")
public interface ImageFeign {
    @PostMapping(value = "/assignimages", headers = "X-Internal=true")
    ResponseEntity<?> assignImages(@RequestBody AssignImageRequest AssignImageRequest);

    @PostMapping(value = "/images", headers = "X-Internal=true")
    ResponseEntity<ImagesResponse> getImages(@RequestBody ImagesRequest ImagesRequest);

}
