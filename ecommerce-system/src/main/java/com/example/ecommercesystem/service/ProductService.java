package com.example.ecommercesystem.service;

import com.example.ecommercesystem.model.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductService {
    List<Product> getAllProducts();
    Product saveProduct(Product product);
    List<Product> getProductsByNameAndDescription(String name, String description);
    Product getProductById(Long id);
    void evictAllProductsCache();
    public void processProductsConcurrently();
    List<String> processProductsWithFutures();
    List<String> processProductsWithCF();

}
