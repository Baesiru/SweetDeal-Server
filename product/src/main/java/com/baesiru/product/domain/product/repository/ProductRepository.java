package com.baesiru.product.domain.product.repository;

import com.baesiru.product.domain.product.repository.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findFirstByIdAndStatusNotOrderByIdDesc(Long id, ProductStatus productStatus);
    List<Product> findByStoreIdAndStatusNotOrderByIdDesc(Long storeId, ProductStatus productStatus);
}
