package com.baesiru.store.domain.store.business;

import com.baesiru.global.annotation.Business;
import com.baesiru.global.api.Api;
import com.baesiru.global.resolver.AuthUser;
import com.baesiru.store.common.errorcode.StoreErrorCode;
import com.baesiru.store.common.exception.store.FailUnregisterStoreException;
import com.baesiru.store.common.exception.store.StoreNotFoundException;
import com.baesiru.store.common.response.MessageResponse;
import com.baesiru.store.domain.store.controller.model.request.LocationRequest;
import com.baesiru.store.domain.store.controller.model.request.RegisterRequest;
import com.baesiru.store.domain.store.controller.model.response.NearbyStoreResponse;
import com.baesiru.store.domain.store.controller.model.response.OwnerStoreResponse;
import com.baesiru.store.domain.store.controller.model.response.UserStoreResponse;
import com.baesiru.store.domain.store.infra.client.UserClient;
import com.baesiru.store.domain.store.infra.client.model.RoleRequest;
import com.baesiru.store.domain.store.infra.client.model.UserRole;
import com.baesiru.store.domain.store.repository.Store;
import com.baesiru.store.domain.store.repository.enums.StoreStatus;
import com.baesiru.store.domain.store.service.StoreService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Business
@Slf4j
public class StoreBusiness {
    @Autowired
    private StoreService storeService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserClient userClient;

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

    public OwnerStoreResponse getOwnStore(AuthUser authUser) {
        Store store = storeService.findFirstByUserIdAndStatusNotOrderByUserIdDesc(Long.parseLong(authUser.getUserId()));
        OwnerStoreResponse ownerStoreResponse = modelMapper.map(store, OwnerStoreResponse.class);
        return ownerStoreResponse;
    }

    public UserStoreResponse getUserStore(Long id) {
        Store store = storeService.findFirstByIdAndStatusOrderByIdDesc(id);
        UserStoreResponse userStoreResponse = modelMapper.map(store, UserStoreResponse.class);
        return userStoreResponse;
    }

    public List<NearbyStoreResponse> getNearbyStore(LocationRequest locationRequest) {
        List<Store> stores = storeService.findStoresWithinRadius(locationRequest.getLatitude(), locationRequest.getLongitude());
        List<NearbyStoreResponse> response = stores.stream()
                .map(store -> modelMapper.map(store, NearbyStoreResponse.class))
                .toList();
        return response;
    }

    @Transactional
    public MessageResponse unregister(Long id, AuthUser authUser) {
        Store store = storeService.findFirstByIdAndStatusNotOrderByIdDesc(id, Long.parseLong(authUser.getUserId()));
        StoreStatus originalStatus = store.getStatus();

        store.setStatus(StoreStatus.UNREGISTERED);
        store.setUnregisteredAt(LocalDateTime.now());
        storeService.save(store);

        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setUserId(authUser.getUserId());
        roleRequest.setRole(UserRole.USER);

        try {
            userClient.changeRole(roleRequest);
            MessageResponse response = new MessageResponse("가게 삭제가 완료되었습니다.");
            return response;
        } catch (FeignException e) {
            rollbackStoreStatus(store, originalStatus);
            throw new FailUnregisterStoreException(StoreErrorCode.FAIL_UNREGISTER_STORE);
        }
    }

    private void rollbackStoreStatus(Store store, StoreStatus originalStatus) {
        store.setStatus(originalStatus);
        store.setUnregisteredAt(null);
        storeService.save(store);
    }
}
