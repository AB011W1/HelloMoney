package com.barclays.bmg.service.response;

import java.math.BigDecimal;

import com.barclays.bmg.context.ResponseContext;

public class InqueryBillServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -1806345124034831701L;
    private BigDecimal outstandingBalanceAmount;
    private BigDecimal maximumBillAmount;
    private BigDecimal minimumBillAmount;
    private String primaryRefNumber;

    public BigDecimal getOutstandingBalanceAmount() {
	return outstandingBalanceAmount;
    }

    public void setOutstandingBalanceAmount(BigDecimal outstandingBalanceAmount) {
	this.outstandingBalanceAmount = outstandingBalanceAmount;
    }

    public BigDecimal getMaximumBillAmount() {
	return maximumBillAmount;
    }

    public void setMaximumBillAmount(BigDecimal maximumBillAmount) {
	this.maximumBillAmount = maximumBillAmount;
    }

    public BigDecimal getMinimumBillAmount() {
	return minimumBillAmount;
    }

    public void setMinimumBillAmount(BigDecimal minimumBillAmount) {
	this.minimumBillAmount = minimumBillAmount;
    }

    public String getPrimaryRefNumber() {
	return primaryRefNumber;
    }

    public void setPrimaryRefNumber(String primaryRefNumber) {
	this.primaryRefNumber = primaryRefNumber;
    }

}
