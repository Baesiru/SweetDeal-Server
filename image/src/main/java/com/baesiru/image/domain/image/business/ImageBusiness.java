package com.baesiru.image.domain.image.business;

import com.baesiru.global.annotation.Business;
import com.baesiru.image.common.config.FileStorageProperties;
import com.baesiru.image.common.response.MessageResponse;
import com.baesiru.image.domain.image.controller.model.request.AssignImageRequest;
import com.baesiru.image.domain.image.controller.model.request.ImageRequest;
import com.baesiru.image.domain.image.controller.model.request.ImagesRequest;
import com.baesiru.image.domain.image.controller.model.response.ImageResponse;
import com.baesiru.image.domain.image.controller.model.response.ImagesResponse;
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

        String extension = originalName.substring(originalName.lastIndexOf(".") + 1);
        String serverName = UUID.randomUUID().toString().replaceAll("-", "") + "." + extension;
        String fileFullPath = uploadDir.resolve(serverName).toAbsolutePath().toString();
        Image image = Image.builder()
                .imageUrl(fileFullPath)
                .serverName(serverName)
                .extension(extension)
                .originalName(originalName)
                .kind(imageRequest.getKind())
                .registeredAt(LocalDateTime.now())
                .build();

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

    public ImagesResponse getImages(ImagesRequest imagesRequest) {
        ImagesResponse response = new ImagesResponse();
        if (imagesRequest.getKind() == ImageKind.STORE) {
            response.setServerNames(imageService.findByStoreIdOrderById(imagesRequest.getStoreId()).stream()
                    .map(Image::getServerName).toList());
        }
        else if (imagesRequest.getKind() == ImageKind.PRODUCT) {
            response.setServerNames(imageService.findByProductIdOrderById(imagesRequest.getProductId()).stream()
                    .map(Image::getServerName).toList());
        }
        return response;
    }
}
