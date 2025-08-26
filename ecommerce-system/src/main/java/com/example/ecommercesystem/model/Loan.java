package com.example.ecommercesystem.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@ToString
@Data
public class Loan {

    // Getters and Setters
    /**
     * ⚔️ The Strategies Explained (with Analogy)
     * Strategy	    Who Generates ID	        When Generated	            Batch Support
     * IDENTITY	    Database (auto-increment)	On INSERT (one at a time)	❌ NO
     * SEQUENCE	    Hibernate via DB sequence	Before INSERT	            ✅ YES
     * TABLE	    Hibernate via a table	    Before INSERT	            ✅ YES
     * AUTO	        Hibernate chooses	        Varies	                    🤷 Depends
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String borrowerName;
    private double amount;
    private double interestRate;
    private int duration; // in months

}
