package com.example.ecommercesystem.controller;

import com.example.ecommercesystem.dto.KafkaMessage;
import com.example.ecommercesystem.dto.ProcessResponse;
import com.example.ecommercesystem.kafka.TestProducer;
import com.example.ecommercesystem.model.Product;
import com.example.ecommercesystem.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private TestProducer testProducer;

    @GetMapping
    public List<Product> getAllProducts(){
        return productServiceImpl.getAllProducts();
    }

    @PostMapping
    // @NotBlank (and all the other Bean Validation annotations like @Size, @Email, etc.) only declare the rule.
    // Spring needs to be told when to enforce those rules → that’s what @Valid does at the controller boundary.
    public Product createProduct(@Valid @RequestBody Product product){
        return productServiceImpl.saveProduct(product);
    }

    @GetMapping("/search")
    public List<Product> searchProduct(@RequestParam String name, @RequestParam(required = false) String description){
        return productServiceImpl.getProductsByNameAndDescription(name,description);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productServiceImpl.getProductById(id);
    }

    // New endpoint to process products concurrently
    @PostMapping("/process")
    public String processProducts() {
        productServiceImpl.processProductsConcurrently();
        return "Products are being processed";
    }

    @PostMapping("/send-test-message")
    public String sendTestMessage(@RequestBody KafkaMessage kafkaMessage) {
        testProducer.sendMessage(kafkaMessage.getMessage());
        return "Message sent to Kafka topic: test_topic";
    }

    @PostMapping("/process-async-with-futures")
    public List<String> processProductsAsync() {
        List<String> details = productServiceImpl.processProductsWithFutures();
        return details;
    }

    @PostMapping("/process-async-with-cf")
    public ProcessResponse  processProductsAsyncWithCF() {
        List<String> details = productServiceImpl.processProductsWithCF();
        return new ProcessResponse("completable future results", details);
    }

}
