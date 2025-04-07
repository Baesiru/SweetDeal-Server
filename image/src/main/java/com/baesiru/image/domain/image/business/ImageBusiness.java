package com.baesiru.image.domain.image.business;

import com.baesiru.global.annotation.Business;
import com.baesiru.image.common.config.FileStorageProperties;
import com.baesiru.image.common.response.MessageResponse;
import com.baesiru.image.domain.image.controller.model.*;
import com.baesiru.image.domain.image.repository.Image;
import com.baesiru.image.domain.image.repository.enums.ImageKind;
import com.baesiru.image.domain.image.service.ImageService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Business
public class ImageBusiness {
    private final Path uploadDir;
    private final ImageService imageService;

    public ImageBusiness(FileStorageProperties fileStorageProperties, ImageService imageService) {
        this.uploadDir = fileStorageProperties.getUploadDir();
        this.imageService = imageService;

        try {
            Files.createDirectories(this.uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("지정된 경로에 폴더를 생성할 수가 없습니다.", e);
        }
    }
    public ImageResponse upload(ImageRequest imageRequest) {
        MultipartFile file = imageRequest.getFile();
        if (file.isEmpty()) {
            throw new RuntimeException();
        }
        String originalName = file.getOriginalFilename();

        String extension = originalName.substring(originalName.lastIndexOf(".")+1);
        String serverName = UUID.randomUUID().toString().replaceAll("-", "") + "." + extension;
        String fileFullPath = uploadDir.resolve(serverName).toAbsolutePath().toString();
        Image image = new Image();

        image.setImageUrl(fileFullPath);
        image.setServerName(serverName);
        image.setExtension(extension);
        image.setOriginalName(originalName);
        image.setKind(imageRequest.getKind());
        image.setRegisteredAt(LocalDateTime.now());

        imageService.save(image);

        try {
            File uploadFile = new File(fileFullPath);
            file.transferTo(uploadFile);
            ImageResponse imageResponse = new ImageResponse(serverName);
            return imageResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public MessageResponse assignImage(AssignImageRequest assignImageRequest) {
        ImageKind imageKind = assignImageRequest.getKind();
        List<String> serverNames = assignImageRequest.getServerNames();
        if (imageKind == ImageKind.STORE) {
            assignStoreImages(assignImageRequest.getStoreId(), serverNames);
        }
        else if (imageKind == ImageKind.PRODUCT) {
            assignProductImages(assignImageRequest.getProductId(), serverNames);
        }
        MessageResponse response = new MessageResponse("이미지와 연동이 완료되었습니다.");
        return response;
    }


    public void assignStoreImages(Long storeId, List<String> serverNames) {
        for (String serverName : serverNames) {
            Image image = imageService.findByServerName(serverName);
            image.setStoreId(storeId);
            imageService.save(image);
        }
    }

    public void assignProductImages(Long productId, List<String> serverNames) {
        for (String serverName : serverNames) {
            Image image = imageService.findByServerName(serverName);
            image.setProductId(productId);
            imageService.save(image);
        }
    }

    public List<ImageResponse> getImages(ImagesRequest imagesRequest) {
        List<ImageResponse> response = new ArrayList<>();
        if (imagesRequest.getKind() == ImageKind.STORE) {
            response = imageService.findByStoreIdOrderById(imagesRequest.getStoreId()).stream()
                    .map(image -> new ImageResponse(image.getServerName())).toList();
        }
        else if (imagesRequest.getKind() == ImageKind.PRODUCT) {
            response = imageService.findByProductIdOrderById(imagesRequest.getProductId()).stream()
                    .map(image -> new ImageResponse(image.getServerName())).toList();
        }
        return response;
    }
}
