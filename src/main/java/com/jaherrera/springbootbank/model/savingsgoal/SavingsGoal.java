package com.jaherrera.springbootbank.model.savingsgoal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jaherrera.springbootbank.model.transaction.Amount;
import lombok.Data;

import java.io.Serializable;

@Data
public class SavingsGoal implements Serializable {

    @JsonProperty("savingsGoalUid")
    private String savingsGoalUid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("savedPercentage")
    private int savedPercentage;

    @JsonProperty("target")
    private Amount target;

    @JsonProperty("totalSaved")
    private Amount totalSaved;

    // Constructors:

    public SavingsGoal(){}

    public SavingsGoal(String savingsGoalUid, String name, int savedPercentage, Amount target, Amount totalSaved) {
        this.savingsGoalUid = savingsGoalUid;
        this.name = name;
        this.savedPercentage = savedPercentage;
        this.target = target;
        this.totalSaved = totalSaved;
    }

    // Getters:

    public String getSavingsGoalUid() {
        return savingsGoalUid;
    }

    public String getName() {
        return name;
    }

    public int getSavedPercentage() {
        return savedPercentage;
    }

    public Amount getTarget() {
        return target;
    }

    public Amount getTotalSaved() {
        return totalSaved;
    }
}
