package com.baesiru.image.domain.image.service;

import com.baesiru.image.domain.image.controller.model.ImageResponse;
import com.baesiru.image.domain.image.repository.Image;
import com.baesiru.image.domain.image.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public void save(Image image) {
        imageRepository.save(image);
    }

    public Image findByServerName(String serverName) {
        Optional<Image> image = imageRepository.findByServerName(serverName);
        if (image.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return image.get();
    }

    public List<Image> findByStoreIdOrderById(Long storeId) {
        List<Image> images = imageRepository.findByStoreIdOrderById(storeId);
        return images;
    }

    public List<Image> findByProductIdOrderById(Long productId) {
        List<Image> images = imageRepository.findByProductIdOrderById(productId);
        return images;
    }
}
