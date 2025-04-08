package com.baesiru.product.domain.product.service;

import com.baesiru.product.domain.product.repository.Product;
import com.baesiru.product.domain.product.repository.ProductRepository;
import com.baesiru.product.domain.product.service.model.image.AssignImageRequest;
import com.netflix.discovery.converters.Auto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
