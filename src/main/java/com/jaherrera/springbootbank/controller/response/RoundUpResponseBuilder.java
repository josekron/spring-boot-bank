package com.jaherrera.springbootbank.controller.response;

import java.math.BigDecimal;

/**
 * RoundUpResponseBuilder: builder for building a RoundUpResponse so the models are separated from the response object
 * that we return to the client.
 */
public class RoundUpResponseBuilder {

    public static RoundUpResponse build(String savingsGoalUid, String savingsGoalName, BigDecimal totalAmount, String currency){
        return new RoundUpResponse(savingsGoalUid, savingsGoalName, totalAmount, currency);
    }
}
