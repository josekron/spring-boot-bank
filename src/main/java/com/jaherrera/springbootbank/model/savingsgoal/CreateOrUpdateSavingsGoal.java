package com.jaherrera.springbootbank.model.savingsgoal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jaherrera.springbootbank.model.ErrorDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateOrUpdateSavingsGoal implements Serializable {

    @JsonProperty("savingsGoalUid")
    private String savingsGoalUid;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("errors")
    private List<ErrorDetail> errors;

    // Constructors:

    public CreateOrUpdateSavingsGoal(){}

    public CreateOrUpdateSavingsGoal(String savingsGoalUid, boolean success, List<ErrorDetail> errors) {
        this.savingsGoalUid = savingsGoalUid;
        this.success = success;
        this.errors = errors;
    }

    // Getters:

    public String getSavingsGoalUid() {
        return savingsGoalUid;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }
}
