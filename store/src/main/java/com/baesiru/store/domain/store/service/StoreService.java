package com.baesiru.store.domain.store.service;

import com.baesiru.store.common.errorcode.StoreErrorCode;
import com.baesiru.store.common.exception.store.BusinessNumberExistsException;
import com.baesiru.store.domain.store.repository.Store;
import com.baesiru.store.domain.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
