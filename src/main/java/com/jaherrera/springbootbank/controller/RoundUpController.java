package com.jaherrera.springbootbank.controller;

import com.jaherrera.springbootbank.controller.response.RoundUpResponseBuilder;
import com.jaherrera.springbootbank.exception.AccountServiceException;
import com.jaherrera.springbootbank.exception.StarlingAPIException;
import com.jaherrera.springbootbank.model.savingsgoal.SavingsGoal;
import com.jaherrera.springbootbank.service.RoundUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/round-up")
public class RoundUpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoundUpController.class);

    @Autowired
    private RoundUpService roundUpService;

    @RequestMapping("")
    public ResponseEntity getRoundUp(@RequestHeader("UserToken") String userToken) {

        try {

            SavingsGoal savingsGoal = roundUpService.roundUpTransactionsIntoSavingsGoal(userToken);

            return ResponseEntity.ok(RoundUpResponseBuilder.build(savingsGoal.getSavingsGoalUid(), savingsGoal.getName(),
                    savingsGoal.getTotalSaved().getMinorUnits(), savingsGoal.getTotalSaved().getCurrency()));

        } catch (AccountServiceException e) {
            LOGGER.error("roundUp - Error: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);

        } catch (StarlingAPIException e) {
            LOGGER.error("roundUp - Error: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.resolve(e.getStatusCode()));
        }
    }

}
