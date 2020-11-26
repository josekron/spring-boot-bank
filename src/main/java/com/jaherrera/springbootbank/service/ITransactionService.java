package com.jaherrera.springbootbank.service;

import com.jaherrera.springbootbank.model.transaction.FeedItemList;

public interface ITransactionService {

    FeedItemList getTransactionsFeed(String accountUid, String categoryUid, String userToken);
}
