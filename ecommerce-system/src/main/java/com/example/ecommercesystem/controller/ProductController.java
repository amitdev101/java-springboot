package com.example.ecommercesystem.controller;

import com.example.ecommercesystem.dto.KafkaMessage;
import com.example.ecommercesystem.dto.ProcessResponse;
import com.example.ecommercesystem.kafka.TestProducer;
import com.example.ecommercesystem.model.Product;
import com.example.ecommercesystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private TestProducer testProducer;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    @GetMapping("/pagelist")
    public Page<Product> list(@PageableDefault(size = 12, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return productService.getAllProducts(pageable);
    }


    @PostMapping
    // @NotBlank (and all the other Bean Validation annotations like @Size, @Email, etc.) only declare the rule.
    // Spring needs to be told when to enforce those rules → that’s what @Valid does at the controller boundary.
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/search")
    public List<Product> searchProduct(@RequestParam String name, @RequestParam(required = false) String description) {
        return productService.getProductsByNameAndDescription(name, description);
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

    @PostMapping("/process-async-with-futures")
    public List<String> processProductsAsync() {
        List<String> details = productService.processProductsWithFutures();
        return details;
    }

    @PostMapping("/process-async-with-cf")
    public ProcessResponse processProductsAsyncWithCF() {
        List<String> details = productService.processProductsWithCF();
        return new ProcessResponse("completable future results", details);
    }

}
