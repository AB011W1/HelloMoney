package com.barclays.bmg.operation.accountdetails.request;

import com.barclays.bmg.context.RequestContext;

public class CreditCardStmtTransOperationRequest extends RequestContext {

    private String accountNo;
    private String cardNo;
    private String statementDate;
    // START
    private String orgCode;

    public String getOrgCode() {
	return orgCode;
    }

    public void setOrgCode(String orgCode) {
	this.orgCode = orgCode;
    }

    // END

    public String getAccountNo() {
	return accountNo;
    }

    public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
    }

    public String getStatementDate() {
	return statementDate;
    }

    public void setStatementDate(String statementDate) {
	this.statementDate = statementDate;
    }

    public String getCardNo() {
	return cardNo;
    }

    public void setCardNo(String cardNo) {
	this.cardNo = cardNo;
    }

}
