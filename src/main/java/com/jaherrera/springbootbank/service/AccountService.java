package com.jaherrera.springbootbank.service;

import com.google.gson.Gson;
import com.jaherrera.springbootbank.exception.StarlingAPIException;
import com.jaherrera.springbootbank.model.HttpClientError;
import com.jaherrera.springbootbank.model.account.Account;
import com.jaherrera.springbootbank.model.account.AccountList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static com.jaherrera.springbootbank.util.HttpUtil.getHeaders;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

@Service
public class AccountService implements IAccountService{

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private static final String ACCOUNT_TYPE_PRIMARY = "PRIMARY";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.v2.account}")
    private String accountsURL;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SavingsGoalService savingsGoalService;

    /**
     * getPrimaryAccount: returns the holder's bank account set up as PRIMARY
     * @param userToken
     * @return
     */
    @Override
    public Account getPrimaryAccount(String userToken){
        AccountList accounts = getAccounts(userToken);
        return accounts.getAccounts().stream()
                .filter(Objects::nonNull)
                .filter(a -> a.getAccountType().equalsIgnoreCase(ACCOUNT_TYPE_PRIMARY))
                .findAny()
                .orElse(null);
    }

    /**
     * getAccounts: returns holder's bank accounts
     * @param userToken
     * @return
     */
    private AccountList getAccounts(String userToken){

        ResponseEntity<AccountList> response = null;

        try{
            response = this.restTemplate.exchange(accountsURL, GET, new HttpEntity<>(null, getHeaders(userToken)), AccountList.class);

        }catch(HttpClientErrorException e){
            LOGGER.error("getAccounts - Error: ", e.getMessage());
            HttpClientError httpClientError = new Gson().fromJson(e.getResponseBodyAsString(), HttpClientError.class);
            throw new StarlingAPIException(e.getRawStatusCode(), httpClientError.getError(), httpClientError.getDescription());
        }

        if (response.getStatusCode() != OK) {
            LOGGER.error("getAccounts - status code: ", response.getStatusCode());
            throw new StarlingAPIException(response.getStatusCode().value());
        }

        return response.getBody();
    }
}
