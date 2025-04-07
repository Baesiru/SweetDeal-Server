package com.baesiru.image.domain.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByServerName(String serverName);

    List<Image> findByStoreIdOrderById(Long storeId);

    List<Image> findByProductIdOrderById(Long productId);
}
