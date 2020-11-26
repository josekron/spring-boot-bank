package com.jaherrera.springbootbank.service;

import com.google.gson.Gson;
import com.jaherrera.springbootbank.exception.StarlingAPIException;
import com.jaherrera.springbootbank.model.HttpClientError;
import com.jaherrera.springbootbank.model.transaction.Amount;
import com.jaherrera.springbootbank.model.transaction.FeedItem;
import com.jaherrera.springbootbank.model.transaction.FeedItemList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.jaherrera.springbootbank.util.BigDecimalUtil.getChangeFromRoundUp;
import static com.jaherrera.springbootbank.util.DateUtil.formatToDateTimeString;
import static com.jaherrera.springbootbank.util.HttpUtil.getHeaders;
import static java.math.BigDecimal.ZERO;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

@Service
public class TransactionService implements ITransactionService{

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.v2.transaction.feed}")
    private String transactionFeedURL;

    /**
     * getTransactionsFeed: returns transactions from an holder's account which were created between two timestamps
     * @param accountUid
     * @param categoryUid
     * @param userToken
     * @return
     */
    @Override
    public FeedItemList getTransactionsFeed(String accountUid, String categoryUid, String userToken){

        ResponseEntity<FeedItemList> response = null;

        final Map<String, String> params = new HashMap<>();
        params.put("accountUid", accountUid);
        params.put("categoryUid", categoryUid);

        LocalDateTime maxDateTime = LocalDateTime.now();
        LocalDateTime minDateTime = maxDateTime.minusWeeks(1);

        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(transactionFeedURL)
                    .queryParam("minTransactionTimestamp", formatToDateTimeString(minDateTime))
                    .queryParam("maxTransactionTimestamp", formatToDateTimeString(maxDateTime));

            response = this.restTemplate.exchange(builder.build().toUriString(), GET, new HttpEntity<>(null, getHeaders(userToken)), FeedItemList.class, params);

        }catch(HttpClientErrorException e){
            LOGGER.error("getTransactionsFeed - Error: ", e.getMessage());
            HttpClientError httpClientError = new Gson().fromJson(e.getResponseBodyAsString(), HttpClientError.class);
            throw new StarlingAPIException(e.getRawStatusCode(), httpClientError.getError(), httpClientError.getDescription());
        }

        if (response.getStatusCode() != OK) {
            LOGGER.error("getTransactionsFeed - status code: ", response.getStatusCode());
            throw new StarlingAPIException(response.getStatusCode().value());
        }

        return response.getBody();
    }

    /**
     * roundUpTransactions: rounds up a list of transactions (FeedItem) and returns the total
     * @param feedItems
     * @return
     */
    public static BigDecimal roundUpTransactions(List<FeedItem> feedItems) {
        return feedItems.stream()
                .filter(Objects::nonNull)
                .map(FeedItem::getAmount)
                .map(Amount::getMinorUnits)
                .reduce(ZERO, (a1, a2) -> a1.add(getChangeFromRoundUp(a2)))
                .movePointRight(2);
    }
}
