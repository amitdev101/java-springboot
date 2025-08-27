package com.example.ecommercesystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Represents a product in the ecommerce system.
 *
 * Implements Serializable for potential future session replication or caching.
 */
@Entity
@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ⚔️ The Strategies Explained (with Analogy)
     *
     * Strategy     | Who Generates ID        | When Generated     | Batch Support
     * -------------|--------------------------|---------------------|----------------
     * IDENTITY     | Database (auto-increment)| On INSERT           | ❌ NO
     * SEQUENCE     | Hibernate via DB sequence| Before INSERT       | ✅ YES
     * TABLE        | Hibernate via a table    | Before INSERT       | ✅ YES
     * AUTO         | Hibernate chooses        | Varies              | 🤷 Depends
     *
     * IDENTITY is chosen here for simplicity, but beware: it disables JDBC batch inserts.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Product name: must not be blank and must fit within DB constraints.
     *
     * - @NotBlank ensures non-empty, non-null input.
     * - @Size ensures upper bound for API validation, avoiding DB errors.
     *
     * Note: Controller must use @Valid to enforce these constraints.
     */
    @Column(length = 120, nullable = false)
    @Size(max = 120, message = "Name must be 120 characters or less")
    @NotBlank(message = "Product name cannot be blank")
    private String name;

    /**
     * Optional product description.
     *
     * Why size constraint?:
     * Prevents excessively long input from causing DB errors or bloating logs.
     */
    @Column(length = 500)
    @Size(max = 500, message = "Description must be 500 characters or less")
    private String description;

    /**
     * Monetary value of the product.
     *
     * Consider: Add @DecimalMin("0.0") and BigDecimal for precision and validation.
     */
    private double price;

    /**
     * Timestamp of when the product was registered in the system.
     *
     * Consider: Use LocalDateTime for Java 8+ time API compatibility.
     */
    private Timestamp registeredTime;

    /**
     * Internal SKU (Stock Keeping Unit) for backend systems.
     *
     * Hidden from JSON serialization to protect internal structure.
     */
    @JsonIgnore
    private String internalSku;

    /**
     * Contact email for product-related queries.
     * Demonstrates email format validation.
     */
    @Email(message = "Please provide a valid contact email")
    @NotNull(message = "Contact email is required")
    private String contactEmail;

    /**
     * Quantity available in stock.
     * Demonstrates numeric constraints.
     */
    @Min(0)
    @Max(1000)
    private int quantityAvailable;

    /**
     * Product weight in kilograms.
     * BigDecimal is preferred for precision.
     */
    @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be greater than 0")
    private BigDecimal weight;

    /**
     * Launch date of the product.
     * Demonstrates temporal validation with past/present restriction.
     */
    @PastOrPresent(message = "Launch date cannot be in the future")
    private LocalDate launchDate;

    /**
     * Expiry date of the product (for perishable goods).
     * Must be in the future.
     */
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;

    /**
     * Enum representing product availability status.
     * Enum is stored as a string for clarity and safety.
     */
    public enum AvailabilityStatus {
        IN_STOCK, OUT_OF_STOCK, PREORDER
    }

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus status;

    /**
     * Many products can belong to a single category.
     *
     * This is the owning side of a bidirectional @OneToMany/@ManyToOne relationship.
     *
     * ⚠️ DO NOT cascade REMOVE from Product to Category!
     * - If CascadeType.REMOVE were applied here, deleting a product would delete its shared category!
     * - Keep category safe by avoiding cascade on this side.
     *
     * ================= FETCH STRATEGIES =================
     *
     * fetch = FetchType.EAGER:
     *   - Loads the related category *immediately* along with the product.
     *   - Good if the category is *always* needed.
     *   - Risks: overfetching and N+1 queries if done in bulk.
     *
     * fetch = FetchType.LAZY:
     *   - Loads the category *on demand*, i.e., only when accessed.
     *   - Great for performance but dangerous outside of transaction scope.
     *   - May throw LazyInitializationException in REST controllers.
     *
     * Best Practice:
     * - Use EAGER only when the association is guaranteed to be accessed.
     * - Use DTOs or fetch joins in service layer to avoid fetch pitfalls.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
