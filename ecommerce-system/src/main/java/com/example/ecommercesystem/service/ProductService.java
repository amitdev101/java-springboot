package com.example.ecommercesystem.service;

import com.example.ecommercesystem.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductService {
    /** New: preferred paginated API */
    Page<Product> getAllProducts(Pageable pageable);

    /** Legacy: keep for callers that still expect a List */
    @Deprecated
    List<Product> getAllProducts();

    Product saveProduct(Product product);

    List<Product> getProductsByNameAndDescription(String name, String description);

    Product getProductById(Long id);

    void evictAllProductsCache();

    public void processProductsConcurrently();

    List<String> processProductsWithFutures();
    
    List<String> processProductsWithCF();

}
