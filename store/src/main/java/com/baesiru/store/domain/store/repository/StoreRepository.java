package com.baesiru.store.domain.store.repository;

import com.baesiru.store.domain.store.repository.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByBusinessNumber(String businessNumber);
    Optional<Store> findFirstByUserIdAndStatusNotOrderByUserIdDesc(Long userId, StoreStatus status);
    Optional<Store> findFirstByUserIdAndStatusOrderByUserIdDesc(Long userId, StoreStatus storeStatus);
    Optional<Store> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreStatus status);
    Optional<Store> findFirstByIdAndUserIdAndStatusNotOrderByIdDesc(Long id, Long userId, StoreStatus status);

    @Query(value = "SELECT * " +
            "FROM store s " +
            "WHERE s.status = :status " +
            "AND ST_Distance_Sphere(point(:lng, :lat), point(s.longitude, s.latitude)) <= :radius ",
            nativeQuery = true)
    List<Store> findStoresWithinRadius(
            @Param("lat") BigDecimal latitude,
            @Param("lng") BigDecimal longitude,
            @Param("radius") BigDecimal radiusInMeters,
            @Param("status") String status
    );

}
