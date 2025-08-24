package com.example.ecommercesystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProcessResponse {
    private String message;
    private List<String> details;

    public ProcessResponse() { }

    public ProcessResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }

}
