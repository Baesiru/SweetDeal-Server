package com.baesiru.image.domain.image.service;

import com.baesiru.image.domain.image.repository.Image;
import com.baesiru.image.domain.image.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public void save(Image image) {
        imageRepository.save(image);
    }
}
