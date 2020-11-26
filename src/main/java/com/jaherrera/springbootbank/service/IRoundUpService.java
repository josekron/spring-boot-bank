package com.jaherrera.springbootbank.service;

import com.jaherrera.springbootbank.exception.AccountServiceException;
import com.jaherrera.springbootbank.model.savingsgoal.SavingsGoal;

public interface IRoundUpService {

    SavingsGoal roundUpTransactionsIntoSavingsGoal(String userToken) throws AccountServiceException;
}
