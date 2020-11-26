package com.jaherrera.springbootbank.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FeedItem implements Serializable {

    @JsonProperty("feedItemUid")
    private String feedItemUid;

    @JsonProperty("categoryUid")
    private String categoryUid;

    @JsonProperty("amount")
    private Amount amount;

    // Getters:

    public String getFeedItemUid() {
        return feedItemUid;
    }

    public String getCategoryUid() {
        return categoryUid;
    }

    public Amount getAmount() {
        return amount;
    }
}
