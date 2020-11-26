package com.jaherrera.springbootbank.service;

import com.jaherrera.springbootbank.model.account.Account;

public interface IAccountService {

    Account getPrimaryAccount(String userToken);
}
