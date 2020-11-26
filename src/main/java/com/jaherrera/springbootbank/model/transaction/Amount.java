package com.jaherrera.springbootbank.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class Amount implements Serializable {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("minorUnits")
    private BigDecimal minorUnits;

    // Constructors:

    public Amount(){}

    public Amount(String currency, BigDecimal minorUnits) {
        this.currency = currency;
        this.minorUnits = minorUnits;
    }

    // Getters:

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getMinorUnits() {
        if(minorUnits == null){
            return BigDecimal.ZERO;
        }
        return minorUnits.movePointLeft(2);
    }
}
