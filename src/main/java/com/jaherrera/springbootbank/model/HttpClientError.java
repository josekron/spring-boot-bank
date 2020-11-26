package com.jaherrera.springbootbank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HttpClientError implements Serializable {

    @JsonProperty("error")
    private String error;

    @JsonProperty("error_description")
    private String error_description;

    // Getters:

    public String getError() {
        return error;
    }

    public String getDescription() {
        return error_description;
    }

    @Override
    public String toString() {
        return "HttpError{" +
                "error='" + error + '\'' +
                ", description='" + error_description + '\'' +
                '}';
    }
}
