package com.baesiru.store.domain.store.service;

import com.baesiru.store.common.errorcode.StoreErrorCode;
import com.baesiru.store.common.exception.store.BusinessNumberExistsException;
import com.baesiru.store.common.exception.store.StoreNotFoundException;
import com.baesiru.store.domain.store.repository.Store;
import com.baesiru.store.domain.store.repository.StoreRepository;
import com.baesiru.store.domain.store.repository.enums.StoreStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    public void save(Store store) {
        storeRepository.save(store);
    }

    public void existsByBusinessNumberWithThrow(String businessNumber) {
        boolean existsByBusinessNumber = storeRepository.existsByBusinessNumber(businessNumber);
        if (existsByBusinessNumber) {
            throw new BusinessNumberExistsException(StoreErrorCode.EXISTS_BUSINESS_NUMBER);
        }
    }

    public Store findFirstByUserIdAndStatusNotOrderByUserIdDesc(Long userId) {
        Optional<Store> store = storeRepository.findFirstByUserIdAndStatusNotOrderByUserIdDesc(userId, StoreStatus.UNREGISTERED);
        if (store.isEmpty()) {
            throw new StoreNotFoundException(StoreErrorCode.STORE_NOT_FOUND);
        }
        return store.get();
    }

    public Store findFirstByIdAndStatusOrderByIdDesc(Long id) {
        Optional<Store> store = storeRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED);
        if (store.isEmpty()) {
            throw new StoreNotFoundException(StoreErrorCode.STORE_NOT_FOUND);
        }
        return store.get();
    }

    public List<Store> findStoresWithinRadius(BigDecimal latitude, BigDecimal longitude) {
        List<Store> stores = storeRepository.findStoresWithinRadius(latitude, longitude, BigDecimal.valueOf(1000.0), StoreStatus.REGISTERED.name());
        return stores;
    }

    public Store findFirstByIdAndStatusNotOrderByIdDesc(Long id, Long userId) {
        Optional<Store> store = storeRepository.findFirstByIdAndUserIdAndStatusNotOrderByIdDesc(id, userId, StoreStatus.UNREGISTERED);
        if (store.isEmpty()) {
            throw new StoreNotFoundException(StoreErrorCode.STORE_NOT_FOUND);
        }
        return store.get();
    }

}
