package com.jaherrera.springbootbank.service;

import com.jaherrera.springbootbank.model.transaction.FeedItem;
import com.jaherrera.springbootbank.model.transaction.FeedItemList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionServiceTest {

    private static String USER_TOKEN_1 = "userToken1";
    private static String USER_TOKEN_2 = "userToken2";

    @Autowired
    private ITransactionService transactionService = (accountUid, categoryUid, userToken) -> {
        FeedItemList feedItemList = null;

        if(userToken.equalsIgnoreCase(USER_TOKEN_1)){
            feedItemList = new FeedItemList(new ArrayList<>());
        }
        else if(userToken.equalsIgnoreCase(USER_TOKEN_2)) {
            List<FeedItem> items = new ArrayList<>();
            items.add(new FeedItem());
            feedItemList = new FeedItemList(items);
        }

        return feedItemList;
    };

    @Test
    public void testTransactionFeed(){
        FeedItemList items = transactionService.getTransactionsFeed(USER_TOKEN_1 + "-Uid", USER_TOKEN_1 + "CTUid", USER_TOKEN_1);
        assertTrue(items.getFeedItems().isEmpty());

        items = transactionService.getTransactionsFeed(USER_TOKEN_2 + "-Uid", USER_TOKEN_2 + "CTUid", USER_TOKEN_2);
        assertTrue(items.getFeedItems().size() == 1);

        items = transactionService.getTransactionsFeed("USER_TOKEN_FAKE" + "-Uid", "USER_TOKEN_FAKE" + "CTUid", "USER_TOKEN_FAKE");
        assertNull(items);
    }
}
