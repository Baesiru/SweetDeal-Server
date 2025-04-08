package com.baesiru.image.domain.image.business;

import com.baesiru.global.annotation.Business;
import com.baesiru.image.common.config.FileStorageProperties;
import com.baesiru.image.common.errorcode.ImageErrorCode;
import com.baesiru.image.common.exception.image.ImageDirectoryErrorException;
import com.baesiru.image.common.exception.image.ImageNotFoundException;
import com.baesiru.image.common.exception.image.ImageUploadException;
import com.baesiru.image.common.response.MessageResponse;
import com.baesiru.image.domain.image.controller.model.request.AssignImageRequest;
import com.baesiru.image.domain.image.controller.model.request.ImageRequest;
import com.baesiru.image.domain.image.controller.model.request.ImagesRequest;
import com.baesiru.image.domain.image.controller.model.response.ImageResponse;
import com.baesiru.image.domain.image.controller.model.response.ImagesResponse;
import com.baesiru.image.domain.image.repository.Image;
import com.baesiru.image.domain.image.repository.enums.ImageKind;
import com.baesiru.image.domain.image.service.ImageService;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public ImageBusiness(FileStorageProperties fileStorageProperties, ImageService imageService, ModelMapper modelMapper) {
        this.uploadDir = fileStorageProperties.getUploadDir();
        this.imageService = imageService;
        this.modelMapper = modelMapper;

        try {
            Files.createDirectories(this.uploadDir);
        } catch (IOException e) {
            throw new ImageDirectoryErrorException(ImageErrorCode.IMAGE_DIRECTORY_ERROR);
        }
    }

    public ImageResponse upload(ImageRequest imageRequest) {
        Image image = modelMapper.map(imageRequest, Image.class);
        image = uploadImage(imageRequest.getFile(), image);
        imageService.save(image);
        ImageResponse imageResponse = modelMapper.map(image, ImageResponse.class);
        return imageResponse;
    }

    private Image uploadImage(MultipartFile file, Image image) {
        if (file.isEmpty()) {
            throw new ImageNotFoundException(ImageErrorCode.IMAGE_NOT_FOUND);
        }
        String originalName = file.getOriginalFilename();

        String extension = originalName.substring(originalName.lastIndexOf(".") + 1);
        String serverName = UUID.randomUUID().toString().replaceAll("-", "") + "." + extension;
        String fileFullPath = uploadDir.resolve(serverName).toAbsolutePath().toString();

        try {
            File uploadFile = new File(fileFullPath);
            file.transferTo(uploadFile);
        } catch (IOException e) {
            throw new ImageUploadException(ImageErrorCode.IMAGE_UPLOAD_ERROR);
        }

        return Image.builder()
                .imageUrl(fileFullPath)
                .serverName(serverName)
                .extension(extension)
                .originalName(originalName)
                .kind(image.getKind())
                .registeredAt(LocalDateTime.now())
                .build();
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
