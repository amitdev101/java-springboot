package com.example.ecommercesystem.service;


import com.example.ecommercesystem.exception.ResourceNotFoundException;
import com.example.ecommercesystem.model.Product;
import com.example.ecommercesystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getProductsByNameAndDescription(String name, String description) {
        return productRepository.findByNameAndDescription(name, description);

    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }


}
