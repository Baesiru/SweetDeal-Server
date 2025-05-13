package com.baesiru.product.domain.product.service;

import com.baesiru.product.common.errorcode.ProductErrorCode;
import com.baesiru.product.common.exception.product.ProductNotFoundException;
import com.baesiru.product.domain.product.controller.model.request.MessageUpdateRequest;
import com.baesiru.product.domain.product.controller.model.response.ProductDetailResponse;
import com.baesiru.product.domain.product.repository.Product;
import com.baesiru.product.domain.product.repository.ProductRepository;
import com.baesiru.product.domain.product.repository.enums.ProductStatus;
import com.baesiru.product.domain.product.service.model.image.AssignImageRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void publishAssignToImage(AssignImageRequest assignImageRequest) {
        rabbitTemplate.convertAndSend("image.topic.exchange", "image.assign", assignImageRequest);
    }

    public void publishUpdateToImage(AssignImageRequest assignImageRequest) {
        rabbitTemplate.convertAndSend("image.topic.exchange", "image.update", assignImageRequest);
    }

    public Product findFirstByIdAndStatusNotOrderByIdDesc(Long id) {
        Optional<Product> product = productRepository.findFirstByIdAndStatusNotOrderByIdDesc(id, ProductStatus.DELETED);
        if (product.isEmpty()) {
            throw new ProductNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }
        return product.get();
    }

    public List<Product> findByStoreIdAndStatusNotOrderByIdDesc(Long storeId) {
        List<Product> product = productRepository.findByStoreIdAndStatusNotOrderByIdDesc(storeId, ProductStatus.DELETED);
        return product;
    }


    public Product findFirstByIdAndStatusOrderByIdDesc(Long id) {
        Optional<Product> product = productRepository.findFirstByIdAndStatusOrderByIdDesc(id, ProductStatus.SALE);
        if (product.isEmpty()) {
            throw new ProductNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }
        return product.get();
    }

    public Product findByIdByPessimisticLock(Long id) {
        Optional<Product> product = productRepository.findByIdByPessimisticLock(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }
        return product.get();
    }

    public void publishCancelProduct(MessageUpdateRequest messageUpdateRequest) {
        rabbitTemplate.convertAndSend("order.topic.exchange", "order.cancel", messageUpdateRequest);
    }
}
