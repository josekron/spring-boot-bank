package com.jaherrera.springbootbank.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class NewTransfer implements Serializable {

    @JsonProperty("amount")
    private Amount amount;

    // Constructors:

    public NewTransfer() {}

    public NewTransfer(Amount amount) {
        this.amount = amount;
    }

    // Getters:

    public Amount getAmount() {
        return amount;
    }
}
