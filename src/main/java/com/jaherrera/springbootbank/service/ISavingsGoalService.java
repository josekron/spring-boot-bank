package com.jaherrera.springbootbank.service;

import com.jaherrera.springbootbank.exception.AccountServiceException;
import com.jaherrera.springbootbank.model.savingsgoal.SavingsGoal;
import com.jaherrera.springbootbank.model.transaction.CreateOrUpdateTransfer;

import java.math.BigDecimal;

public interface ISavingsGoalService {

    SavingsGoal createSavingsGoal(String accountUid, String userToken, String savingsGoalName, BigDecimal amount) throws AccountServiceException;

    SavingsGoal getSavingsGoals(String accountUid, String userToken, String savingsGoalUid);

    CreateOrUpdateTransfer addMoneySavingsGoal(String accountUid, String userToken, String savingsGoalUid, BigDecimal minorUnits, String currency);
}
