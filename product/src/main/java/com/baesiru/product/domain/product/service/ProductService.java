package com.baesiru.product.domain.product.service;

import com.baesiru.product.domain.product.repository.Product;
import com.baesiru.product.domain.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void save(Product product) {
        productRepository.save(product);
    }
}
