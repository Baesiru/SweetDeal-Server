package com.baesiru.product.domain.product.business;

import com.baesiru.global.annotation.Business;
import com.baesiru.global.resolver.AuthUser;
import com.baesiru.product.common.errorcode.ProductErrorCode;
import com.baesiru.product.common.exception.product.ProductDeleteDenyException;
import com.baesiru.product.common.exception.product.ProductNotFoundException;
import com.baesiru.product.common.exception.product.UnauthorizedStoreAccessException;
import com.baesiru.product.common.exception.product.WrongProductInformationException;
import com.baesiru.product.common.response.MessageResponse;
import com.baesiru.product.domain.product.controller.model.request.MessageUpdateRequest;
import com.baesiru.product.domain.product.controller.model.request.ProductCreateRequest;
import com.baesiru.product.domain.product.controller.model.request.ProductInternalRequest;
import com.baesiru.product.domain.product.controller.model.request.ProductUpdateRequest;
import com.baesiru.product.domain.product.controller.model.response.ProductDetailResponse;
import com.baesiru.product.domain.product.controller.model.response.ProductInform;
import com.baesiru.product.domain.product.controller.model.response.ProductInternalResponse;
import com.baesiru.product.domain.product.controller.model.response.ProductsResponse;
import com.baesiru.product.domain.product.repository.Product;
import com.baesiru.product.domain.product.repository.enums.ProductStatus;
import com.baesiru.product.domain.product.service.ProductService;
import com.baesiru.product.domain.product.service.StoreFeign;
import com.baesiru.product.domain.product.service.model.image.AssignImageRequest;
import com.baesiru.product.domain.product.service.model.image.ImageKind;
import com.baesiru.product.domain.product.service.model.order.OrderItemRequest;
import com.baesiru.product.domain.product.service.model.store.StoreProductResponse;
import com.baesiru.product.domain.product.service.model.store.StoreSimpleResponse;
import feign.FeignException;
import org.hibernate.dialect.lock.PessimisticEntityLockException;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Business
public class ProductBusiness {
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StoreFeign storeFeign;

    @Transactional
    public MessageResponse create(ProductCreateRequest productCreateRequest, AuthUser authUser) {
        if (LocalDateTime.now().isAfter(productCreateRequest.getExpiredAt())
                || LocalDateTime.now().isAfter(productCreateRequest.getSaleClosedAt())) {
            throw new WrongProductInformationException(ProductErrorCode.WRONG_PRODUCT_INFORMATION);
        }
        Long storeId;
        try {
            ResponseEntity<StoreSimpleResponse> response = storeFeign.getStore(authUser.getUserId());
            storeId = response.getBody().getId();
        } catch (FeignException e) {
            throw new UnauthorizedStoreAccessException(ProductErrorCode.UNAUTHORIZED_STORE_ACCESS);
        }
        Product product = modelMapper.map(productCreateRequest, Product.class);
        product.setStoreId(storeId);
        product.setRegisteredAt(LocalDateTime.now());
        product.setStatus(ProductStatus.SALE);
        if (product.getExpiredAt().isAfter(productCreateRequest.getSaleClosedAt())) {
            product.setExpiredAt(productCreateRequest.getSaleClosedAt());
        }
        product = productService.save(product);
        AssignImageRequest assignImageRequest = AssignImageRequest.builder()
                .kind(ImageKind.PRODUCT)
                .productId(product.getId())
                .serverNames(productCreateRequest.getServerNames())
                .build();
        productService.publishAssignToImage(assignImageRequest);

        MessageResponse messageResponse = new MessageResponse("상품이 정상적으로 등록되었습니다.");
        return messageResponse;
    }

    public ProductDetailResponse getProductDetail(Long id) {
        Product product = productService.findFirstByIdAndStatusNotOrderByIdDesc(id);
        ProductDetailResponse productDetailResponse = modelMapper.map(product, ProductDetailResponse.class);
        try {
            ResponseEntity<StoreProductResponse> responseEntity = storeFeign.getProductStore(product.getStoreId());
            productDetailResponse.setStoreName(responseEntity.getBody().getName());
        }
        catch (FeignException e) {
            throw new ProductNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }
        return productDetailResponse;
    }

    @Transactional
    public MessageResponse delete(Long id, AuthUser authUser) {
        Product product = productService.findFirstByIdAndStatusNotOrderByIdDesc(id);
        ProductStatus productStatus = product.getStatus();
        try {
            ResponseEntity<StoreSimpleResponse> response = storeFeign.getStore(authUser.getUserId());
        } catch (FeignException e) {
            throw new UnauthorizedStoreAccessException(ProductErrorCode.UNAUTHORIZED_STORE_ACCESS);
        }

        if (productStatus == ProductStatus.RESERVED || productStatus == ProductStatus.SOLD) {
            throw new ProductDeleteDenyException(ProductErrorCode.PRODUCT_DELETE_DENY);
        }
        product.setStatus(ProductStatus.DELETED);
        product.setExpiredAt(LocalDateTime.now());
        productService.save(product);
        MessageResponse response = new MessageResponse("상품이 삭제되었습니다.");
        return response;
    }

    public ProductsResponse getProducts(Long storeId) {
        List<Product> products = productService.findByStoreIdAndStatusNotOrderByIdDesc(storeId);
        List<ProductDetailResponse> productDetailResponses = products.stream()
                .map(product -> modelMapper.map(product, ProductDetailResponse.class)).toList();
        ProductsResponse productsResponse = new ProductsResponse();
        productsResponse.setStoreId(storeId);
        productsResponse.setProducts(productDetailResponses);
        return productsResponse;
    }

    public MessageResponse update(Long id, ProductUpdateRequest productUpdateRequest,  AuthUser authUser) {
        if (LocalDateTime.now().isAfter(productUpdateRequest.getExpiredAt())
                || LocalDateTime.now().isAfter(productUpdateRequest.getSaleClosedAt())) {
            throw new WrongProductInformationException(ProductErrorCode.WRONG_PRODUCT_INFORMATION);
        }

        Product product = productService.findFirstByIdAndStatusNotOrderByIdDesc(id);
        try {
            ResponseEntity<StoreSimpleResponse> response = storeFeign.getStore(authUser.getUserId());
        } catch (FeignException e) {
            throw new UnauthorizedStoreAccessException(ProductErrorCode.UNAUTHORIZED_STORE_ACCESS);
        }
        product.setCount(productUpdateRequest.getCount());
        product.setOriginalPrice(productUpdateRequest.getOriginalPrice());
        product.setDiscountedPrice(productUpdateRequest.getDiscountedPrice());
        product.setDescription(productUpdateRequest.getDescription());

        if (product.getExpiredAt().isAfter(productUpdateRequest.getSaleClosedAt())) {
            product.setExpiredAt(productUpdateRequest.getSaleClosedAt());
        }
        productService.save(product);
        AssignImageRequest assignImageRequest = AssignImageRequest.builder()
                .kind(ImageKind.PRODUCT)
                .productId(product.getId())
                .serverNames(productUpdateRequest.getServerNames())
                .build();
        productService.publishUpdateToImage(assignImageRequest);

        MessageResponse messageResponse = new MessageResponse("상품 수정이 완료되었습니다.");
        return messageResponse;

    }

    public ProductInternalResponse getProductInternal(ProductInternalRequest productInternalRequest) {
        List<ProductInform> productInforms = new ArrayList<>();
        for (Long id : productInternalRequest.getProductIds()) {
            Product product = productService.findFirstByIdAndStatusNotOrderByIdDesc(id);
            ProductInform productInform = modelMapper.map(product, ProductInform.class);
            productInforms.add(productInform);
        }
        ProductInternalResponse productInternalResponse = new ProductInternalResponse(productInforms);
        return productInternalResponse;
    }

    @Transactional
    @RabbitListener(queues = "product.update.queue")
    @Retryable(
            value = {PessimisticEntityLockException.class, CannotAcquireLockException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 200)
    )
    public void handlerUpdateProduct(MessageUpdateRequest messageUpdateRequest) {
        List<OrderItemRequest> orderItemRequests = messageUpdateRequest.getOrderItemRequests();
        for (OrderItemRequest orderItemRequest : orderItemRequests) {
            Product product = productService.findByIdByPessimisticLock(orderItemRequest.getProductId());
            if (product.getCount() < orderItemRequest.getCount()) {
                // 메시지 큐로 주문 상태 취소 로직 추가
                productService.publishCancelProduct(null);
                throw new WrongProductInformationException(ProductErrorCode.WRONG_PRODUCT_INFORMATION);
            }
            product.setCount(product.getCount() - orderItemRequest.getCount());
            if (product.getCount() == 0L) {
                product.setStatus(ProductStatus.SOLD);
            }
            productService.save(product);
        }

    }

    @Transactional
    @RabbitListener(queues = "product.cancel.queue")
    public void handlerCancelProduct(MessageUpdateRequest messageUpdateRequest) {
        List<OrderItemRequest> orderItemRequests = messageUpdateRequest.getOrderItemRequests();
        for (OrderItemRequest orderItemRequest : orderItemRequests) {
            Product product = productService.findByIdByPessimisticLock(orderItemRequest.getProductId());
            product.setCount(product.getCount() + orderItemRequest.getCount());
            if (product.getStatus() == ProductStatus.SOLD) {
                product.setStatus(ProductStatus.SALE);
            }
            productService.save(product);
        }
    }

}
