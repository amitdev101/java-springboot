package com.example.ecommercesystem.controller;

import com.example.ecommercesystem.dto.KafkaMessage;
import com.example.ecommercesystem.kafka.TestProducer;
import com.example.ecommercesystem.model.Product;
import com.example.ecommercesystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private TestProducer testProducer;

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @GetMapping("/search")
    public List<Product> searchProduct(@RequestParam String name, @RequestParam(required = false) String description){
        return productService.getProductsByNameAndDescription(name,description);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // New endpoint to process products concurrently
    @PostMapping("/process")
    public String processProducts() {
        productService.processProductsConcurrently();
        return "Products are being processed";
    }

    @PostMapping("/send-test-message")
    public String sendTestMessage(@RequestBody KafkaMessage kafkaMessage) {
        testProducer.sendMessage(kafkaMessage.getMessage());
        return "Message sent to Kafka topic: test_topic";
    }

}
