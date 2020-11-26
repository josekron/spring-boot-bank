package com.jaherrera.springbootbank.service;

import com.jaherrera.springbootbank.exception.AccountServiceException;
import com.jaherrera.springbootbank.model.account.Account;
import com.jaherrera.springbootbank.model.savingsgoal.SavingsGoal;
import com.jaherrera.springbootbank.model.transaction.FeedItemList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RoundUpService implements IRoundUpService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RoundUpService.class);

    private static String SAVINGS_GOAL_NAME_DEFAULT = "Starling Test";

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SavingsGoalService savingsGoalService;

    /**
     * roundUpTransactionsIntoSavingsGoal: round up all the transactions from holder's primary account and transferred into a new savings goal.
     * @param userToken
     * @return
     * @throws AccountServiceException
     */
    @Override
    public SavingsGoal roundUpTransactionsIntoSavingsGoal(String userToken) throws AccountServiceException {

        // get account:
        Account primaryAccount = accountService.getPrimaryAccount(userToken);
        if(primaryAccount == null){
            LOGGER.error("Primary account not found");
            throw new AccountServiceException("Primary account not found");
        }

        // get transactions:
        FeedItemList items = transactionService.getTransactionsFeed(primaryAccount.getAccountUid(), primaryAccount.getDefaultCategory(), userToken);
        BigDecimal amountRoundedUp = transactionService.roundUpTransactions(items.getFeedItems());

        if(amountRoundedUp.compareTo(BigDecimal.ZERO) <= 0){
            LOGGER.error("No enough amount for a savings goal from accountUid {}", primaryAccount.getAccountUid());
            throw new AccountServiceException("No enough amount for a savings goal");
        }

        // create savings goal with amount:
        return savingsGoalService.createSavingsGoal(primaryAccount.getAccountUid(), userToken, SAVINGS_GOAL_NAME_DEFAULT, amountRoundedUp);
    }
}
