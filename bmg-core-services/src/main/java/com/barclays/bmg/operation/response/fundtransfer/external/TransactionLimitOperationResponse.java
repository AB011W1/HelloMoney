package com.barclays.bmg.operation.response.fundtransfer.external;

import java.math.BigDecimal;

import com.barclays.bmg.context.ResponseContext;

public class TransactionLimitOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -8515289023112522103L;

    private BigDecimal aValidDailyLimit;
    private boolean authRequired = false;
    private String authType;

    public BigDecimal getAValidDailyLimit() {
	return aValidDailyLimit;
    }

    public void setAValidDailyLimit(BigDecimal validDailyLimit) {
	aValidDailyLimit = validDailyLimit;
    }

    public boolean isAuthRequired() {
	return authRequired;
    }

    public void setAuthRequired(boolean authRequired) {
	this.authRequired = authRequired;
    }

    public String getAuthType() {
	return authType;
    }

    public void setAuthType(String authType) {
	this.authType = authType;
    }

}
