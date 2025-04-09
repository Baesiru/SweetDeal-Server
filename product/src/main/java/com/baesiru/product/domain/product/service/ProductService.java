package com.baesiru.product.domain.product.service;

import com.baesiru.product.common.errorcode.ProductErrorCode;
import com.baesiru.product.common.exception.product.ProductNotFoundException;
import com.baesiru.product.domain.product.repository.Product;
import com.baesiru.product.domain.product.repository.ProductRepository;
import com.baesiru.product.domain.product.repository.enums.ProductStatus;
import com.baesiru.product.domain.product.service.model.image.AssignImageRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        rabbitTemplate.convertAndSend("image.topic.exchange", "image.store.assign", assignImageRequest);
    }

    public Product findFirstByIdAndStatusNotOrderByIdDesc(Long id) {
        Optional<Product> product = productRepository.findFirstByIdAndStatusNotOrderByIdDesc(id, ProductStatus.DELETED);
        if (product.isEmpty()) {
            throw new ProductNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }
        return product.get();
    }
}
