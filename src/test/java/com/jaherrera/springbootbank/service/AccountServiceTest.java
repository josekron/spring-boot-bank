package com.jaherrera.springbootbank.service;

import com.jaherrera.springbootbank.model.account.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountServiceTest {

    private static String USER_TOKEN_1 = "userToken1";
    private static String USER_TOKEN_2 = "userToken2";

    @Autowired
    private IAccountService accountService = userToken -> {
        Account account = null;

        if(userToken.equalsIgnoreCase(USER_TOKEN_1)){
            account = new Account(USER_TOKEN_1 + "-Uid", null, null, null, null, null);
        }
        else if(userToken.equalsIgnoreCase(USER_TOKEN_2)) {
            account = new Account(USER_TOKEN_2 + "-Uid", null, null, null, null, null);
        }

        return account;
    };

    @Test
    public void testAccountService(){
        Account account = accountService.getPrimaryAccount(USER_TOKEN_1);
        assertTrue(account.getAccountUid().equalsIgnoreCase(USER_TOKEN_1 + "-Uid"));

        account = accountService.getPrimaryAccount(USER_TOKEN_2);
        assertTrue(account.getAccountUid().equalsIgnoreCase(USER_TOKEN_2 + "-Uid"));

        account = accountService.getPrimaryAccount("USER_TOKEN_FAIL");
        assertNull(account);
    }
}
