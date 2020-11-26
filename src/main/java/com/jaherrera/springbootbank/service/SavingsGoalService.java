package com.jaherrera.springbootbank.service;

import com.google.gson.Gson;
import com.jaherrera.springbootbank.exception.AccountServiceException;
import com.jaherrera.springbootbank.exception.StarlingAPIException;
import com.jaherrera.springbootbank.model.HttpClientError;
import com.jaherrera.springbootbank.model.savingsgoal.CreateOrUpdateSavingsGoal;
import com.jaherrera.springbootbank.model.savingsgoal.NewSavingsGoal;
import com.jaherrera.springbootbank.model.savingsgoal.SavingsGoal;
import com.jaherrera.springbootbank.model.transaction.Amount;
import com.jaherrera.springbootbank.model.transaction.CreateOrUpdateTransfer;
import com.jaherrera.springbootbank.model.transaction.NewTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.jaherrera.springbootbank.util.HttpUtil.getHeaders;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.OK;

@Service
public class SavingsGoalService implements ISavingsGoalService{

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsGoalService.class);

    private static final String DEFAULT_CURRENCY = "GBP";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.v2.savings-goal}")
    private String savingsGoalURL;

    @Value("${api.v2.savings-goal.add-money}")
    private String savingsGoalAddMoneyURL;

    /**
     * createSavingsGoal: create a new savings goal, add a transfer to total amount and return the updated savings goal.
     * @param accountUid
     * @param userToken
     * @param savingsGoalName
     * @param totalSaved
     * @return
     * @throws AccountServiceException
     */
    @Override
    public SavingsGoal createSavingsGoal(String accountUid, String userToken, String savingsGoalName, BigDecimal totalSaved) throws AccountServiceException {
        // create savings goal:
        CreateOrUpdateSavingsGoal newSavingsGoal = createNewSavingsGoal(accountUid, userToken, savingsGoalName, DEFAULT_CURRENCY);

        if(!newSavingsGoal.isSuccess()){
            LOGGER.error("Error creating savings goals for accountUid {} : {}", accountUid, newSavingsGoal.getErrors().get(0).getMessage());
            throw new AccountServiceException(newSavingsGoal.getErrors().get(0).getMessage());
        }

        // add money into savings goal:
        CreateOrUpdateTransfer newTransfer = addMoneySavingsGoal(accountUid, userToken,
                newSavingsGoal.getSavingsGoalUid(), totalSaved, DEFAULT_CURRENCY);

        if(!newTransfer.isSuccess()){
            LOGGER.error("Error creating transfer for accountUid {} : {}", accountUid, newTransfer.getErrors().get(0).getMessage());
            throw new AccountServiceException(newTransfer.getErrors().get(0).getMessage());
        }

        // Get savings goal:
        SavingsGoal updatedSavingsGoal = getSavingsGoals(accountUid, userToken, newSavingsGoal.getSavingsGoalUid());
        LOGGER.debug("roundUp accountUid: {} transferUid: {} savingsGoalUid: {}", accountUid, newTransfer.getTransferUid(), newSavingsGoal.getSavingsGoalUid());
        return updatedSavingsGoal;
    }

    /**
     * createNewSavingsGoal: create a new savings goal without any amount.
     * @param accountUid
     * @param userToken
     * @param savingsGoalName
     * @param savingsGoalsCurrency
     * @return
     */
    private CreateOrUpdateSavingsGoal createNewSavingsGoal(String accountUid, String userToken, String savingsGoalName, String savingsGoalsCurrency){

        ResponseEntity<CreateOrUpdateSavingsGoal> response = null;

        final Map<String, String> params = new HashMap<>();
        params.put("accountUid", accountUid);

        try{
            HttpEntity<NewSavingsGoal> entity = getSavingsGoalEntity(userToken, savingsGoalName, savingsGoalsCurrency);

            response = this.restTemplate.exchange(savingsGoalURL, PUT, entity, CreateOrUpdateSavingsGoal.class, params);

        }catch(HttpClientErrorException e){
            LOGGER.error("createSavingGoals - Error: ", e.getMessage());
            HttpClientError httpClientError = new Gson().fromJson(e.getResponseBodyAsString(), HttpClientError.class);
            throw new StarlingAPIException(e.getRawStatusCode(), httpClientError.getError(), httpClientError.getDescription());
        }

        if (response.getStatusCode() != OK) {
            LOGGER.error("createSavingGoals - status code: ", response.getStatusCode());
            throw new StarlingAPIException(response.getStatusCode().value());
        }

        return response.getBody();
    }

    /**
     * getSavingsGoals: return the savings goal by accountUid and savingsGoalUid
     * @param accountUid
     * @param userToken
     * @param savingsGoalUid
     * @return
     */
    @Override
    public SavingsGoal getSavingsGoals(String accountUid, String userToken, String savingsGoalUid){

        ResponseEntity<SavingsGoal> response = null;

        final Map<String, String> params = new HashMap<>();
        params.put("accountUid", accountUid);

        String sgURL = savingsGoalURL + "/" + savingsGoalUid;

        try{
            response = this.restTemplate.exchange(sgURL, GET, new HttpEntity<>(null, getHeaders(userToken)), SavingsGoal.class, params);

        }catch(HttpClientErrorException e){
            LOGGER.error("getSavingGoals - Error: ", e.getMessage());
            HttpClientError httpClientError = new Gson().fromJson(e.getResponseBodyAsString(), HttpClientError.class);
            throw new StarlingAPIException(e.getRawStatusCode(), httpClientError.getError(), httpClientError.getDescription());
        }

        if (response.getStatusCode() != OK) {
            LOGGER.error("getSavingGoals - status code: ", response.getStatusCode());
            throw new StarlingAPIException(response.getStatusCode().value());
        }

        return response.getBody();
    }

    /**
     * addMoneySavingsGoal: add money into a savings goal and returns the transfer created
     * @param accountUid
     * @param userToken
     * @param savingsGoalUid
     * @param minorUnits
     * @param currency
     * @return
     */
    @Override
    public CreateOrUpdateTransfer addMoneySavingsGoal(String accountUid, String userToken, String savingsGoalUid, BigDecimal minorUnits, String currency){

        ResponseEntity<CreateOrUpdateTransfer> response = null;

        final Map<String, String> params = new HashMap<>();
        params.put("accountUid", accountUid);
        params.put("savingsGoalUid", savingsGoalUid);
        params.put("transferUid", UUID.randomUUID().toString());

        try{
            HttpEntity<NewTransfer> entity = getAddMoneySavingsGoalEntity(userToken, minorUnits, currency);

            response = this.restTemplate.exchange(savingsGoalAddMoneyURL, PUT, entity, CreateOrUpdateTransfer.class, params);

        }catch(HttpClientErrorException e){
            LOGGER.error("addMoneySavingsGoal - Error: ", e.getMessage());
            HttpClientError httpClientError = new Gson().fromJson(e.getResponseBodyAsString(), HttpClientError.class);
            throw new StarlingAPIException(e.getRawStatusCode(), httpClientError.getError(), httpClientError.getDescription());
        }

        if (response.getStatusCode() != OK) {
            LOGGER.error("addMoneySavingsGoal - status code: ", response.getStatusCode());
            throw new StarlingAPIException(response.getStatusCode().value());
        }

        return response.getBody();
    }

    private HttpEntity<NewSavingsGoal> getSavingsGoalEntity(String userToken, String savingsGoalName, String savingsGoalCurrency) {
        final HttpHeaders httpHeaders = getHeaders(userToken);
        final NewSavingsGoal newSavingsGoal = new NewSavingsGoal(savingsGoalName, savingsGoalCurrency);

        return new HttpEntity<>(newSavingsGoal, httpHeaders);
    }

    private HttpEntity<NewTransfer> getAddMoneySavingsGoalEntity(String userToken, BigDecimal minorUnits, String currency) {
        final HttpHeaders httpHeaders = getHeaders(userToken);

        minorUnits = minorUnits.movePointRight(2).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        final Amount newAmount = new Amount(currency, minorUnits);
        final NewTransfer newTransfer = new NewTransfer(newAmount);

        return new HttpEntity<>(newTransfer, httpHeaders);
    }
}
