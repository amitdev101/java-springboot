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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String borrowerName;
    private double amount;
    private double interestRate;
    private int duration; // in months

}
