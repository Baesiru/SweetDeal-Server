package com.baesiru.store.domain.store.business;

import com.baesiru.global.annotation.Business;
import com.baesiru.global.resolver.AuthUser;
import com.baesiru.store.common.response.MessageResponse;
import com.baesiru.store.domain.store.controller.model.request.RegisterRequest;
import com.baesiru.store.domain.store.repository.Store;
import com.baesiru.store.domain.store.repository.enums.StoreStatus;
import com.baesiru.store.domain.store.service.StoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Business
public class StoreBusiness {
    @Autowired
    private StoreService storeService;
    @Autowired
    private ModelMapper modelMapper;

    public MessageResponse register(RegisterRequest registerRequest, AuthUser authUser) {
        storeService.existsByBusinessNumberWithThrow(registerRequest.getBusinessNumber());
        Store store = modelMapper.map(registerRequest, Store.class);
        store.setUserId(Long.parseLong(authUser.getUserId()));
        store.setRequestedAt(LocalDateTime.now());
        store.setStatus(StoreStatus.PENDING);
        storeService.save(store);
        MessageResponse response = new MessageResponse("가게 등록 요청이 완료되었습니다.");
        return response;
    }
}
