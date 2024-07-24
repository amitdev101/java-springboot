package com.example.ecommercesystem.repository;

import com.example.ecommercesystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    // Automatically generated queries based on method names
    List<Product> findByName(String name);
    List<Product> findByDescription(String description);
    List<Product> findByPrice(double price);

    List<Product> findByNameAndPrice(String name, double price);
    List<Product> findByNameContaining(String keyword);
    List<Product> findByPriceGreaterThan(double price);
    List<Product> findByPriceBetween(double minPrice, double maxPrice);
    List<Product> findByNameAndDescription(String name, String description);
    List<Product> findByNameContainingAndDescriptionContaining(String name, String description);

    long countByName(String name);
    boolean existsByName(String name);

    void deleteByName(String name);

//    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
//    List<Product> findByNameContaining(@Param("name") String name);

    // Example of handling null values
    @Query("SELECT p FROM Product p WHERE " +
            "(:name IS NULL OR p.name = :name) AND " +
            "(:description IS NULL OR p.description = :description) OR " +
            "(:name IS NOT NULL AND :description IS NULL) OR " +
            "(:name IS NULL AND :description IS NOT NULL)")
    List<Product> findByNameOrDescription(@Param("name") String name,
                                          @Param("description") String description);
}
