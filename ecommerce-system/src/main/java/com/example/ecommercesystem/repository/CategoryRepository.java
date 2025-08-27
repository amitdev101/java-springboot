package com.example.ecommercesystem.repository;

import com.example.ecommercesystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    /**
     * Finds category by name.
     * Useful for ensuring uniqueness and dropdown list selections.
     */
    Category findByName(String name);


    /**
     * Checks if a category exists by name.
     * Prevents duplication before insertion.
     */
    boolean existsByName(String name);


    /**
     * Finds all categories with product count greater than specified.
     * Advanced JPQL usage.
     *
     * @Param("minParam") → Demonstrates flexibility in param naming
     * Allows method param name to differ from JPQL variable
     */
    @Query("SELECT c FROM Category c WHERE size(c.products) > :minParam")
    List<Category> findCategoriesWithMoreThanXProducts(@Param("minParam") int min);
}
