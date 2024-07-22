package com.example.ecommercesystem.service;


import com.example.ecommercesystem.exception.ResourceNotFoundException;
import com.example.ecommercesystem.model.Product;
import com.example.ecommercesystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Cache the list of all products
    @Cacheable(value = "products")
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    // Save a product and update the cache
    @CachePut(value = "product", key = "#product.id")
    @CacheEvict(value = "products", allEntries = true)
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getProductsByNameAndDescription(String name, String description) {
        return productRepository.findByNameAndDescription(name, description);

    }

    // Cache a product by ID
    @Cacheable(value = "product", key = "#id")
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    @CacheEvict(value = "products", allEntries = true)
    public void evictAllProductsCache() {
        // This method will clear the 'products' cache
    }


}
