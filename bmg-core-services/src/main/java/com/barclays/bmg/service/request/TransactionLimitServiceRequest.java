package com.barclays.bmg.service.request;

import java.math.BigDecimal;

import com.barclays.bmg.context.RequestContext;

public class TransactionLimitServiceRequest extends RequestContext {
    private BigDecimal amountInLCY;
    private String txnType;

    public BigDecimal getAmountInLCY() {
	return amountInLCY;
    }

    public void setAmountInLCY(BigDecimal amountInLCY) {
	this.amountInLCY = amountInLCY;
    }

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}


}
