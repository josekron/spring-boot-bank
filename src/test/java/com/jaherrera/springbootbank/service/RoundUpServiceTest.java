package com.jaherrera.springbootbank.service;

import com.jaherrera.springbootbank.exception.AccountServiceException;
import com.jaherrera.springbootbank.model.savingsgoal.SavingsGoal;
import com.jaherrera.springbootbank.model.transaction.Amount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class RoundUpServiceTest {

    private static String USER_TOKEN_1 = "userToken1";
    private static String USER_TOKEN_2 = "userToken2";

    @Autowired
    private IRoundUpService roundUpService = userToken -> {
        SavingsGoal savingsGoal = null;

        if(userToken.equalsIgnoreCase(USER_TOKEN_1)){
            Amount amount = new Amount("GBP", new BigDecimal(2000));
            savingsGoal = new SavingsGoal(USER_TOKEN_1 + "-SGUid", USER_TOKEN_1 + "-NAME", 0, null, amount);
        }
        else if(userToken.equalsIgnoreCase(USER_TOKEN_2)) {
            Amount amount = new Amount("GBP", new BigDecimal(1000));
            savingsGoal = new SavingsGoal(USER_TOKEN_2 + "-SGUid", USER_TOKEN_2 + "-NAME", 0, null, amount);
        }
        else{
            throw new AccountServiceException("error");
        }

        return savingsGoal;
    };

    @Test
    public void testRoundUp() {
        try {
            SavingsGoal savingsGoal = roundUpService.roundUpTransactionsIntoSavingsGoal(USER_TOKEN_1);
            assertTrue(savingsGoal.getSavingsGoalUid().equalsIgnoreCase(USER_TOKEN_1 + "-SGUid"));
            assertTrue(savingsGoal.getTotalSaved().getMinorUnits().compareTo(new BigDecimal(20.00)) == 0);

            savingsGoal = roundUpService.roundUpTransactionsIntoSavingsGoal(USER_TOKEN_2);
            assertTrue(savingsGoal.getSavingsGoalUid().equalsIgnoreCase(USER_TOKEN_2 + "-SGUid"));
            assertTrue(savingsGoal.getTotalSaved().getMinorUnits().compareTo(new BigDecimal(10.00)) == 0);

        } catch (AccountServiceException e) {
            fail();
        }
    }

    @Test
    public void testRoundUpException() {
        try {
            roundUpService.roundUpTransactionsIntoSavingsGoal("USER_TOKEN_FAIL");
            fail();
        } catch (AccountServiceException e) {
            assertTrue(true);
        }
    }

}
