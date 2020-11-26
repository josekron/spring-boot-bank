package com.jaherrera.springbootbank.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RoundUpResponse implements Serializable {

    @JsonProperty("savingsGoalUid")
    private String savingsGoalUid;

    @JsonProperty("savingsName")
    private String savingsName;

    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;

    @JsonProperty("currency")
    private String currency;

    // Constructors:

    public RoundUpResponse(String savingsGoalUid, String savingsName, BigDecimal totalAmount, String currency) {
        this.savingsGoalUid = savingsGoalUid;
        this.savingsName = savingsName;
        this.totalAmount = totalAmount;
        this.currency = currency;
    }
}

