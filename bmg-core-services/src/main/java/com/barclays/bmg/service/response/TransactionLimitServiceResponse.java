package com.barclays.bmg.service.response;

import java.math.BigDecimal;

import com.barclays.bmg.context.ResponseContext;

public class TransactionLimitServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -7435912678105060334L;
    private BigDecimal thresholdLimit;
    private BigDecimal transactionLimit;
    private BigDecimal aValidDailyLimit;
    private boolean authRequired = false;
    private BigDecimal transactionMaxLimitUB;
    private BigDecimal transactionMinLimitUB;
    private int maxTxns;
    private int maxBenfLimit;
    private int maxBillerBenfLimit;
    private int payeeNickNameLengthMax;
    private int maxDaysTD;
    private int minDaysTD;
    private int lastPaidBillRecords;

    public BigDecimal getThresholdLimit() {
	return thresholdLimit;
    }

    public void setThresholdLimit(BigDecimal thresholdLimit) {
	this.thresholdLimit = thresholdLimit;
    }

    public BigDecimal getTransactionLimit() {
	return transactionLimit;
    }

    public void setTransactionLimit(BigDecimal transactionLimit) {
	this.transactionLimit = transactionLimit;
    }

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

    public BigDecimal getTransactionMaxLimitUB() {
	return transactionMaxLimitUB;
    }

    public void setTransactionMaxLimitUB(BigDecimal transactionMaxLimitUB) {
	this.transactionMaxLimitUB = transactionMaxLimitUB;
    }

    public BigDecimal getTransactionMinLimitUB() {
	return transactionMinLimitUB;
    }

    public void setTransactionMinLimitUB(BigDecimal transactionMinLimitUB) {
	this.transactionMinLimitUB = transactionMinLimitUB;
    }

    public int getMaxTxns() {
	return maxTxns;
    }

    public void setMaxTxns(int maxTxns) {
	this.maxTxns = maxTxns;
    }

    public int getMaxBenfLimit() {
	return maxBenfLimit;
    }

    public void setMaxBenfLimit(int maxBenfLimit) {
	this.maxBenfLimit = maxBenfLimit;
    }

    /**
     * @return the maxBillerBenfLimit
     */
    public int getMaxBillerBenfLimit() {
	return maxBillerBenfLimit;
    }

    /**
     * @param maxBillerBenfLimit
     *            the maxBillerBenfLimit to set
     */
    public void setMaxBillerBenfLimit(int maxBillerBenfLimit) {
	this.maxBillerBenfLimit = maxBillerBenfLimit;
    }

    /**
     * @return the payeeNickNameLengthMax
     */
    public int getPayeeNickNameLengthMax() {
	return payeeNickNameLengthMax;
    }

    /**
     * @param payeeNickNameLengthMax
     *            the payeeNickNameLengthMax to set
     */
    public void setPayeeNickNameLengthMax(int payeeNickNameLengthMax) {
	this.payeeNickNameLengthMax = payeeNickNameLengthMax;
    }

    public int getMaxDaysTD() {
	return maxDaysTD;
    }

    public void setMaxDaysTD(int maxDaysTD) {
	this.maxDaysTD = maxDaysTD;
    }

    public int getMinDaysTD() {
	return minDaysTD;
    }

    public void setMinDaysTD(int minDaysTD) {
	this.minDaysTD = minDaysTD;
    }

    public int getLastPaidBillRecords() {
	return lastPaidBillRecords;
    }

    public void setLastPaidBillRecords(int lastPaidBillRecords) {
	this.lastPaidBillRecords = lastPaidBillRecords;
    }

}
