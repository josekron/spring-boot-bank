package com.jaherrera.springbootbank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorDetail implements Serializable {

    @JsonProperty("message")
    private String message;

    // Getters:

    public String getMessage() {
        return message;
    }
}
