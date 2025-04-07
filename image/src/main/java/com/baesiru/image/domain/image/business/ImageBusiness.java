package com.baesiru.image.domain.image.business;

import com.baesiru.global.annotation.Business;
import com.baesiru.image.common.config.FileStorageProperties;
import com.baesiru.image.common.response.MessageResponse;
import com.baesiru.image.domain.image.controller.model.ImageRequest;
import com.baesiru.image.domain.image.repository.Image;
import com.baesiru.image.domain.image.service.ImageService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public MessageResponse upload(ImageRequest imageRequest) {
        MultipartFile file = imageRequest.getFile();
        if (file.isEmpty()) {
            throw new RuntimeException();
        }
        String originalName = file.getOriginalFilename();
        String serverName = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String fileFullPath = uploadDir.resolve(serverName + "." + extension).toAbsolutePath().toString();
        Image image = new Image();

        image.setImageUrl(fileFullPath);
        image.setServerName(serverName);
        image.setExtension(extension);
        image.setOriginalName(originalName);
        image.setKind(imageRequest.getKind());

        imageService.save(image);

        try {
            File uploadFile = new File(fileFullPath);
            file.transferTo(uploadFile);
            MessageResponse response = new MessageResponse("이미지 저장이 완료되었습니다.");
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
