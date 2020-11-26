package com.jaherrera.springbootbank.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AccountList implements Serializable {

    @JsonProperty("accounts")
    private List<Account> accounts;

    // Getters:

    public List<Account> getAccounts(){
        return accounts;
    }
}