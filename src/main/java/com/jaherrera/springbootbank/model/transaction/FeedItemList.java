package com.jaherrera.springbootbank.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FeedItemList {

    @JsonProperty("feedItems")
    private List<FeedItem> feedItems;

    // Constructors:

    public FeedItemList(){}

    public FeedItemList(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }

    // Getters:

    public List<FeedItem> getFeedItems() {
        return feedItems;
    }
}
