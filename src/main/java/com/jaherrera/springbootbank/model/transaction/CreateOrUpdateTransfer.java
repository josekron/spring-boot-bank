package com.jaherrera.springbootbank.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jaherrera.springbootbank.model.ErrorDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateOrUpdateTransfer implements Serializable {

    @JsonProperty("transferUid")
    private String transferUid;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("errors")
    private List<ErrorDetail> errors;

    // Constructors:

    public CreateOrUpdateTransfer(){}

    public CreateOrUpdateTransfer(String transferUid, boolean success, List<ErrorDetail> errors) {
        this.transferUid = transferUid;
        this.success = success;
        this.errors = errors;
    }

    // Getters:

    public String getTransferUid() {
        return transferUid;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }
}
