package com.jaherrera.springbootbank.service;

import com.jaherrera.springbootbank.exception.AccountServiceException;
import com.jaherrera.springbootbank.model.savingsgoal.SavingsGoal;
import com.jaherrera.springbootbank.model.transaction.Amount;
import com.jaherrera.springbootbank.model.transaction.CreateOrUpdateTransfer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SavingsGoalServiceTest {

    private static String USER_TOKEN_1 = "userToken1";
    private static String USER_TOKEN_2 = "userToken2";

    @Autowired
    private ISavingsGoalService savingsGoalService = new ISavingsGoalService() {

        @Override
        public SavingsGoal getSavingsGoals(String accountUid, String userToken, String savingsGoalUid) {
            SavingsGoal savingsGoal = null;

            if(userToken.equalsIgnoreCase(USER_TOKEN_1)){
                Amount amount = new Amount("GBP", new BigDecimal(2000));
                savingsGoal = new SavingsGoal(USER_TOKEN_1 + "-SGUid", USER_TOKEN_1 + "-NAME", 0, null, amount);
            }
            else if(userToken.equalsIgnoreCase(USER_TOKEN_2)) {
                Amount amount = new Amount("GBP", new BigDecimal(1000));
                savingsGoal = new SavingsGoal(USER_TOKEN_2 + "-SGUid", USER_TOKEN_2 + "-NAME", 0, null, amount);
            }

            return savingsGoal;
        }

        @Override
        public SavingsGoal createSavingsGoal(String accountUid, String userToken, String savingsGoalName, BigDecimal amount) {
            SavingsGoal savingsGoal = null;

            if(userToken.equalsIgnoreCase(USER_TOKEN_1)){
                savingsGoal = new SavingsGoal(USER_TOKEN_1 + "-SGUid", null, 0, null, null);
            }
            else if(userToken.equalsIgnoreCase(USER_TOKEN_2)) {
                savingsGoal = new SavingsGoal(USER_TOKEN_2 + "-SGUid", null, 0, null, null);
            }

            return savingsGoal;
        }

        @Override
        public CreateOrUpdateTransfer addMoneySavingsGoal(String accountUid, String userToken, String savingsGoalUid, BigDecimal minorUnits, String currency) {
            CreateOrUpdateTransfer couTransfer = null;

            if(userToken.equalsIgnoreCase(USER_TOKEN_1)){
                couTransfer = new CreateOrUpdateTransfer(USER_TOKEN_1 + "-TUid", true, null);
            }
            else if(userToken.equalsIgnoreCase(USER_TOKEN_2)) {
                couTransfer = new CreateOrUpdateTransfer(USER_TOKEN_2 + "-TUid", false, null);
            }

            return couTransfer;
        }
    };

    @Test
    public void testSavingsGoal(){
        SavingsGoal savingsGoal = savingsGoalService.getSavingsGoals("USER_ACCOUNT_1", USER_TOKEN_1, "SG_1");
        assertTrue(savingsGoal.getSavingsGoalUid().equalsIgnoreCase(USER_TOKEN_1 + "-SGUid"));

        savingsGoal = savingsGoalService.getSavingsGoals("USER_ACCOUNT_2", USER_TOKEN_2, "SG_2");
        assertTrue(savingsGoal.getSavingsGoalUid().equalsIgnoreCase(USER_TOKEN_2 + "-SGUid"));

        savingsGoal = savingsGoalService.getSavingsGoals("USER_ACCOUNT_FAKE", "USER_TOKEN_FAIL", "SG_2");
        assertNull(savingsGoal);
    }

    @Test
    public void testCreateSavingsGoal() throws AccountServiceException {
        SavingsGoal savingsGoal = savingsGoalService.createSavingsGoal("USER_ACCOUNT_1", USER_TOKEN_1, null, null);
        assertTrue(savingsGoal.getSavingsGoalUid().equalsIgnoreCase(USER_TOKEN_1 + "-SGUid"));

        savingsGoal = savingsGoalService.createSavingsGoal("USER_ACCOUNT_2", USER_TOKEN_2, null, null);
        assertTrue(savingsGoal.getSavingsGoalUid().equalsIgnoreCase(USER_TOKEN_2 + "-SGUid"));

        savingsGoal = savingsGoalService.createSavingsGoal("USER_ACCOUNT_FAKE", "USER_TOKEN_FAIL", null, null);
        assertNull(savingsGoal);
    }

    @Test
    public void testAddMoneySavingsGoal(){
        CreateOrUpdateTransfer transfer = savingsGoalService.addMoneySavingsGoal("USER_ACCOUNT_1", USER_TOKEN_1, null, null, null);
        assertTrue(transfer.getTransferUid().equalsIgnoreCase(USER_TOKEN_1 + "-TUid"));

        transfer = savingsGoalService.addMoneySavingsGoal("USER_ACCOUNT_2", USER_TOKEN_2, null, null, null);
        assertTrue(transfer.getTransferUid().equalsIgnoreCase(USER_TOKEN_2 + "-TUid"));

        transfer = savingsGoalService.addMoneySavingsGoal("USER_ACCOUNT_FAKE", "USER_TOKEN_FAKE", null, null, null);
        assertNull(transfer);
    }
}
