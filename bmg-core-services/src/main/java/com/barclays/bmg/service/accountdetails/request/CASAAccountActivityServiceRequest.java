package com.barclays.bmg.service.accountdetails.request;

import java.math.BigDecimal;
import java.util.Date;

import com.barclays.bmg.context.RequestContext;

public class CASAAccountActivityServiceRequest extends RequestContext {
    private String accountNo;
    private int days;

    private String currentPeriodSelected = "radioTransactionDays";
    private String transactionDays = "0";
    private Date startDate;
    private Date endDate = new Date();
    private boolean statementTrxFlag;
    private String branchCode;

    private BigDecimal fromAmount = new BigDecimal(0.00);
    private BigDecimal endAmount = new BigDecimal(0.00);

    private int pageNo;

    public String getAccountNo() {
	return accountNo;
    }

    public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
    }

    public int getDays() {
	return days;
    }

    public void setDays(int days) {
	this.days = days;
    }

    public String getCurrentPeriodSelected() {
	return currentPeriodSelected;
    }

    public void setCurrentPeriodSelected(String currentPeriodSelected) {
	this.currentPeriodSelected = currentPeriodSelected;
    }

    public String getTransactionDays() {
	return transactionDays;
    }

    public void setTransactionDays(String transactionDays) {
	this.transactionDays = transactionDays;
    }

    public Date getStartDate() {
	return new Date(startDate.getTime());
    }

    public void setStartDate(Date startDate) {
	if (startDate == null) {
	    this.startDate = null;
	} else {
	    this.startDate = new Date(startDate.getTime());
	}
    }

    public Date getEndDate() {
	return new Date(endDate.getTime());
    }

    public void setEndDate(Date endDate) {
	if (endDate == null) {
	    this.endDate = null;
	} else {
	    this.endDate = new Date(endDate.getTime());
	}
    }

    public boolean isStatementTrxFlag() {
	return statementTrxFlag;
    }

    public void setStatementTrxFlag(boolean statementTrxFlag) {
	this.statementTrxFlag = statementTrxFlag;
    }

    public String getBranchCode() {
	return branchCode;
    }

    public void setBranchCode(String branchCode) {
	this.branchCode = branchCode;
    }

    public BigDecimal getFromAmount() {
	return fromAmount;
    }

    public void setFromAmount(BigDecimal fromAmount) {
	this.fromAmount = fromAmount;
    }

    public BigDecimal getEndAmount() {
	return endAmount;
    }

    public void setEndAmount(BigDecimal endAmount) {
	this.endAmount = endAmount;
    }

    public int getPageNo() {
	return pageNo;
    }

    public void setPageNo(int pageNo) {
	this.pageNo = pageNo;
    }

}
