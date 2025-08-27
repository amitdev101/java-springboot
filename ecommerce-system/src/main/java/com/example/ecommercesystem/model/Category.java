package com.example.ecommercesystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a product category in the ecommerce system.
 *
 * A Category is a parent entity that groups multiple Product instances.
 * This structure enables filtering, organizing, and managing products at scale.
 *
 * ⚔️ JPA RELATIONSHIP TYPES EXPLAINED
 * ======================================================
 *
 * 1. @OneToOne
 *    - Each instance is linked to exactly one other.
 *    - Example: User ↔ Profile, Employee ↔ WorkStation
 *    - Use @JoinColumn to define FK.
 *    - Often bidirectional (use mappedBy on inverse).
 *
 * 2. @OneToMany
 *    - One parent has many children.
 *    - Example: Category ↔ Products, Department ↔ Employees
 *    - Use mappedBy to indicate the child owns the FK.
 *    - Common to use with List/Set for children.
 *    - orphanRemoval = true auto-deletes detached children.
 *
 * 3. @ManyToOne
 *    - Many entities refer to the same parent.
 *    - Example: Many Products → One Category
 *    - This side owns the FK and uses @JoinColumn.
 *
 * 4. @ManyToMany
 *    - Both sides have multiple links to the other.
 *    - Example: Students ↔ Courses, Users ↔ Roles
 *    - Requires @JoinTable with @JoinColumn and inverse.
 *    - Use Set<> to avoid duplicate joins.
 *    - For metadata (e.g. date of enrollment), use a separate join entity.
 *
 * ⚙️ CASCADE TYPES IN JPA (javax.persistence.CascadeType)
 * ======================================================
 *
 * Cascade defines how operations performed on the parent are propagated to its children.
 *
 * CascadeType.PERSIST      → Propagates persist() → Saves new child entities automatically.
 * CascadeType.MERGE        → Propagates merge() → Updates changes to children.
 * CascadeType.REMOVE       → Propagates remove() → Deletes child entities.
 * CascadeType.REFRESH      → Propagates refresh() → Reloads children from DB.
 * CascadeType.DETACH       → Propagates detach() → Detaches child from persistence context.
 * CascadeType.ALL          → Applies all of the above.
 *
 * 🔥 Common Pitfall:
 *    - Do NOT use CascadeType.ALL blindly in @ManyToOne (e.g., Product → Category),
 *      or deleting a product might delete the shared category as well!
 */
@Entity
@Data // Lombok generates getters, setters, equals, hashCode, and toString
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key for the category table.
     *
     * GenerationType.IDENTITY delegates ID creation to the database (auto-increment).
     * This is simple but not batch-insert friendly.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the category.
     *
     * - @Column enforces DB-level size and nullability constraints.
     * - @Size ensures the name length doesn't exceed DB capacity during validation.
     * - @NotBlank ensures the string is not null and not just whitespace.
     *
     * Why use validation?:
     * → Prevent invalid or malformed data from reaching the database layer.
     */
    @Column(length = 100, nullable = false)
    @Size(max = 100, message = "Category name must be 100 characters or less")
    @NotBlank(message = "Category name cannot be blank")
    private String name;

    /**
     * Optional textual description of the category.
     *
     * @Size prevents unnecessarily long input.
     * This helps avoid DB constraint violations and improves API efficiency.
     */
    @Size(max = 500, message = "Description must be 500 characters or less")
    private String description;

    /**
     * List of products under this category.
     *
     * @OneToMany defines a one-to-many relationship from Category to Product.
     * mappedBy = "category" tells JPA that the Product entity owns the relationship.
     *
     * cascade = CascadeType.ALL:
     *   - Saves new products automatically when saving the category.
     *   - Deletes all products if the category is deleted.
     *   - Updates children during merges.
     *   - Avoids manual product management.
     *
     * orphanRemoval = true:
     *   - If a product is removed from the products list, it is also removed from the DB.
     *   - Helps keep DB in sync with in-memory object graph.
     *
     * @JsonManagedReference handles bidirectional JSON serialization by marking this as the parent side.
     * Without this, a circular reference exception will be thrown when converting to JSON.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Product> products;
}
