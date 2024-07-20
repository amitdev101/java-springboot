package com.example.ecommercesystem.repository;

import com.example.ecommercesystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
