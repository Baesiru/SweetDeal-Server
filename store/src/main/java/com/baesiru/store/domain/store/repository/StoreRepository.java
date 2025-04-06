package com.baesiru.store.domain.store.repository;

import com.baesiru.store.domain.store.repository.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByBusinessNumber(String businessNumber);
    Optional<Store> findFirstByUserIdAndStatusNotOrderByUserIdDesc(Long userId, StoreStatus status);
    Optional<Store> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreStatus status);
}
