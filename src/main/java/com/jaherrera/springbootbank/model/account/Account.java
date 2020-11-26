package com.jaherrera.springbootbank.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Account implements Serializable {

    @JsonProperty("accountUid")
    private String accountUid;

    @JsonProperty("accountType")
    private String accountType;

    @JsonProperty("defaultCategory")
    private String defaultCategory;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("name")
    private String name;

    // Constructors:

    public Account(){}

    public Account(String accountUid, String accountType, String defaultCategory, String currency, String createdAt, String name) {
        this.accountUid = accountUid;
        this.accountType = accountType;
        this.defaultCategory = defaultCategory;
        this.currency = currency;
        this.createdAt = createdAt;
        this.name = name;
    }

    // Getters

    public String getAccountUid() {
        return accountUid;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getDefaultCategory() {
        return defaultCategory;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountUid='" + accountUid + '\'' +
                ", accountType='" + accountType + '\'' +
                ", defaultCategory='" + defaultCategory + '\'' +
                ", currency='" + currency + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
