package com.example.ecommercesystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 120, nullable = false)
    @Size(max = 120, message = "Name must be 120 characters or less")
    @NotBlank
    // Without this, Bean Validation imposes no default limit.
    // The API can accept very long strings until DB constraints reject them.
    // @NotBlank (and all the other Bean Validation annotations like @Size, @Email, etc.) only declare the rule.
    // Spring needs to be told when to enforce those rules → that’s what @Valid does at the controller boundary.
    private String name;

    @Column(length = 500)
    @Size(max = 500, message = "Description must be 500 characters or less")
    private String description;

    private double price;
    // let's add a column in the table
    private Timestamp registeredTime;
    @JsonIgnore
    private String internalSku;
}
