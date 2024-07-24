package com.example.ecommercesystem.service;


import com.example.ecommercesystem.exception.ResourceNotFoundException;
import com.example.ecommercesystem.model.Product;
import com.example.ecommercesystem.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    private ExecutorService executorService;
    private final Random random = new Random();


    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(10); // Initialize a thread pool with 10 threads
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }

    // Cache the list of all products
//    @Cacheable(value = "products")
    public List<Product> getAllProducts(){
        logger.info("getting all products from db.");
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

    // Method to process products using a thread pool
    public void processProductsConcurrently() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            executorService.submit(() -> processProduct(product));
        }
    }

    private void processProduct(Product product) {
        logger.info("Processing product: {} {}", product.getName(), product.getId());
        try {
            Thread.sleep(random.nextInt(2000)); // Simulate random processing time upto 2sec
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.info("Product processing interrupted: {} {}",
                    product.getName(),
                    product.getId());
        }
        logger.info("Product processed: {} {}", product.getName(), product.getId());
    }

}
