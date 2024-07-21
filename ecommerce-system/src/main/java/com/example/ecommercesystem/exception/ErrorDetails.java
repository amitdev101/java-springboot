package com.example.ecommercesystem.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ErrorDetails {
    // Getters and Setters
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

}
