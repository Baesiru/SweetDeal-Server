package com.baesiru.product.domain.product.repository;

import com.baesiru.product.domain.product.repository.enums.ProductStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findFirstByIdAndStatusNotOrderByIdDesc(Long id, ProductStatus productStatus);
    List<Product> findByStoreIdAndStatusNotOrderByIdDesc(Long storeId, ProductStatus productStatus);
    Optional<Product> findFirstByIdAndStatusOrderByIdDesc(Long id, ProductStatus productStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdByPessimisticLock(Long id);
}
