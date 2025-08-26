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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final Random random = new Random();

    @Autowired
    private ProductRepository productRepository;
    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(10); // Initialize a thread pool with 10 threads
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }

    // Cache the list of all products
    @Cacheable(value = "products")
    @Override
    public List<Product> getAllProducts() {
        logger.info("getting all products from db.");
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }


    // Save a product and update the cache
    @CachePut(value = "product", key = "#product.id")
    @CacheEvict(value = "products", allEntries = true)
    public Product saveProduct(Product product) {
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


    public List<String> processProductsWithFutures() {
        List<Product> products = productRepository.findAll();
        List<Future<String>> futures = new ArrayList<>();
        for (Product p : products) {
            futures.add(
                    executorService.submit(() -> getProductDetails(p))
            );
        }

        // block & gather results in same order
        List<String> results = new ArrayList<>();
        for (Future<String> f : futures) {
            try {
                results.add(f.get());     // blocks until each is done
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                results.add("ERROR: " + e.getMessage());
            }
        }
        return results;
    }

    public List<String> processProductsWithCF() {
        List<Product> products = productRepository.findAll();

        // 1) Kick off all tasks
        List<CompletableFuture<String>> cfs = products.stream()
                .map(p -> CompletableFuture
                        .supplyAsync(() -> getProductDetails(p), executorService))
                .collect(Collectors.toList());

        // 2) Wait for all to complete
        CompletableFuture
                .allOf(cfs.toArray(new CompletableFuture[0]))
                .join();  // non‐throws CompletionException on error

        // 3) Collect ordered results
        return cfs.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }


    private String getProductDetails(Product product) {
        return product.toString();
    }

    private void processProduct(Product product) {
        logger.info("Processing product: {} id: {}", product.getName(), product.getId());
        try {
            Thread.sleep(random.nextInt(2000)); // Simulate random processing time upto 2sec
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.info("Product processing interrupted: {} {}", product.getName(), product.getId());
        }
        logger.info("Product processed: {} {}", product.getName(), product.getId());
    }

}
