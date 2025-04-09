package com.baesiru.store.domain.store.business;

import com.baesiru.global.annotation.Business;
import com.baesiru.global.resolver.AuthUser;
import com.baesiru.store.common.errorcode.StoreErrorCode;
import com.baesiru.store.common.exception.store.FailFetchStoreException;
import com.baesiru.store.common.exception.store.FailRegisterStoreException;
import com.baesiru.store.common.exception.store.FailUnregisterStoreException;
import com.baesiru.store.common.response.MessageResponse;
import com.baesiru.store.domain.store.controller.model.request.LocationRequest;
import com.baesiru.store.domain.store.controller.model.request.RegisterRequest;
import com.baesiru.store.domain.store.controller.model.request.UpdateRequest;
import com.baesiru.store.domain.store.controller.model.response.NearbyStoreResponse;
import com.baesiru.store.domain.store.controller.model.response.OwnerStoreResponse;
import com.baesiru.store.domain.store.controller.model.response.StoreSimpleResponse;
import com.baesiru.store.domain.store.controller.model.response.UserStoreResponse;
import com.baesiru.store.domain.store.service.ImageFeign;
import com.baesiru.store.domain.store.service.UserFeign;
import com.baesiru.store.domain.store.service.model.image.AssignImageRequest;
import com.baesiru.store.domain.store.service.model.image.ImageKind;
import com.baesiru.store.domain.store.service.model.image.ImagesRequest;
import com.baesiru.store.domain.store.service.model.image.ImagesResponse;
import com.baesiru.store.domain.store.service.model.user.RoleRequest;
import com.baesiru.store.domain.store.service.model.user.UserRole;
import com.baesiru.store.domain.store.repository.Store;
import com.baesiru.store.domain.store.repository.enums.StoreStatus;
import com.baesiru.store.domain.store.service.StoreService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Business
@Slf4j
public class StoreBusiness {
    @Autowired
    private StoreService storeService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private ImageFeign imageFeign;

    public MessageResponse register(RegisterRequest registerRequest, AuthUser authUser) {
        storeService.existsByBusinessNumberWithThrow(registerRequest.getBusinessNumber());
        Store store = modelMapper.map(registerRequest, Store.class);
        store.setUserId(Long.parseLong(authUser.getUserId()));
        store.setRequestedAt(LocalDateTime.now());
        store.setStatus(StoreStatus.PENDING);
        store = storeService.save(store);

        AssignImageRequest assignImageRequest = AssignImageRequest.builder()
                .kind(ImageKind.STORE)
                .storeId(store.getId())
                .serverNames(registerRequest.getServerNames())
                .build();
        storeService.publishAssignToImage(assignImageRequest);

        MessageResponse response = new MessageResponse("가게 등록 요청이 완료되었습니다.");
        return response;
    }

    public MessageResponse update(UpdateRequest updateRequest, AuthUser authUser) {
        Store store = storeService.findFirstByUserIdAndStatusNotOrderByUserIdDesc(Long.parseLong(authUser.getUserId()));
        modelMapper.map(updateRequest, store);
        AssignImageRequest assignImageRequest = AssignImageRequest.builder()
                .kind(ImageKind.STORE)
                .storeId(store.getId())
                .serverNames(updateRequest.getServerNames())
                .build();

        storeService.publishUpdateToImage(assignImageRequest);
        MessageResponse response = new MessageResponse("가게 수정 요청이 완료되었습니다.");
        return response;
    }

    public OwnerStoreResponse getOwnStore(AuthUser authUser) {
        Store store = storeService.findFirstByUserIdAndStatusNotOrderByUserIdDesc(Long.parseLong(authUser.getUserId()));
        OwnerStoreResponse ownerStoreResponse = modelMapper.map(store, OwnerStoreResponse.class);

        ImagesRequest imagesRequest = new ImagesRequest();
        imagesRequest.setStoreId(store.getId());
        imagesRequest.setKind(ImageKind.STORE);
        try {
            ResponseEntity<ImagesResponse> response = imageFeign.getImages(imagesRequest);
            ownerStoreResponse.setServerNames(response.getBody().getServerNames());
        } catch (FeignException e) {
            throw new FailFetchStoreException(StoreErrorCode.FAIL_FETCH_STORE);
        }
        return ownerStoreResponse;
    }

    public UserStoreResponse getUserStore(Long id) {
        Store store = storeService.findFirstByIdAndStatusOrderByIdDesc(id);
        UserStoreResponse userStoreResponse = modelMapper.map(store, UserStoreResponse.class);

        ImagesRequest imagesRequest = new ImagesRequest();
        imagesRequest.setStoreId(store.getId());
        imagesRequest.setKind(ImageKind.STORE);
        try {
            ResponseEntity<ImagesResponse> response = imageFeign.getImages(imagesRequest);
            userStoreResponse.setServerNames(response.getBody().getServerNames());
            return userStoreResponse;
        } catch (FeignException e) {
            throw new FailFetchStoreException(StoreErrorCode.FAIL_FETCH_STORE);
        }
    }

    public List<NearbyStoreResponse> getNearbyStore(LocationRequest locationRequest) {
        List<Store> stores = storeService.findStoresWithinRadius(locationRequest.getLatitude(), locationRequest.getLongitude());
        List<NearbyStoreResponse> response = stores.stream()
                .map(store -> modelMapper.map(store, NearbyStoreResponse.class))
                .toList();
        return response;
    }

    @Transactional
    public MessageResponse unregister(AuthUser authUser) {
        Store store = storeService.findFirstByUserIdAndStatusNotOrderByUserIdDesc(Long.parseLong(authUser.getUserId()));
        StoreStatus originalStatus = store.getStatus();

        store.setStatus(StoreStatus.UNREGISTERED);
        store.setUnregisteredAt(LocalDateTime.now());
        storeService.save(store);

        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setUserId(authUser.getUserId());
        roleRequest.setRole(UserRole.USER);

        try {
            userFeign.changeRole(roleRequest);
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


    public StoreSimpleResponse getSimpleStore(String userId) {
        Store store = storeService.findFirstByUserIdAndStatusOrderByUserIdDesc(Long.parseLong(userId));
        StoreSimpleResponse storeSimpleResponse = modelMapper.map(store, StoreSimpleResponse.class);
        return storeSimpleResponse;
    }
}
