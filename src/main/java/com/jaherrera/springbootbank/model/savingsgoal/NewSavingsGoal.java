package com.jaherrera.springbootbank.model.savingsgoal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class NewSavingsGoal implements Serializable {

    @JsonProperty("name")
    private String name;

    @JsonProperty("currency")
    private String currency;

    public NewSavingsGoal(String name, String currency) {
        this.name = name;
        this.currency = currency;
    }
}
