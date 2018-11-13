package com.barclays.bmg.service.accountdetails.request;

import java.util.Date;

import com.barclays.bmg.context.RequestContext;

public class CreditCardAccountActivityServiceRequest extends RequestContext {

    private String accountNumber;
    private boolean statementTrxFlag;
    private Date statementDate;
    private Date startDate;
    private Date endDate;
    // ORCHARD CHANGES START
    private String orgCode;

    public String getOrgCode() {
	return orgCode;
    }

    public void setOrgCode(String orgCode) {
	this.orgCode = orgCode;
    }

    // ORCHARD CHANGES END

    public String getAccountNumber() {
	return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
    }

    public boolean isStatementTrxFlag() {
	return statementTrxFlag;
    }

    public void setStatementTrxFlag(boolean statementTrxFlag) {
	this.statementTrxFlag = statementTrxFlag;
    }

    public Date getStatementDate() {
	return statementDate;
    }

    public void setStatementDate(Date statementDate) {
	this.statementDate = statementDate;
    }

    public Date getStartDate() {
	return startDate;
    }

    public void setStartDate(Date startDate) {
	this.startDate = startDate;
    }

    public Date getEndDate() {
	return endDate;
    }

    public void setEndDate(Date endDate) {
	this.endDate = endDate;
    }

}
