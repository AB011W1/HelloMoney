package com.barclays.bmg.service.accountdetails.request;

import com.barclays.bmg.context.RequestContext;

public class CreditCardStatementDatesServiceRequest extends RequestContext {

    private String accountNo;
    // ORCHARD CHANGES START
    private String orgCode;

    public String getOrgCode() {
	return orgCode;
    }

    public void setOrgCode(String orgCode) {
	this.orgCode = orgCode;
    }

    // ORCHARD CHANGES START

    public String getAccountNo() {
	return accountNo;
    }

    public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
    }

}
