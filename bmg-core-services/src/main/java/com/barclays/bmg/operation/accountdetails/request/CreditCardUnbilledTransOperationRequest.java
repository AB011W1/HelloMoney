package com.barclays.bmg.operation.accountdetails.request;

import com.barclays.bmg.context.RequestContext;

public class CreditCardUnbilledTransOperationRequest extends RequestContext {

    private String accountNo;
    private String cardNo;

    public String getAccountNo() {
	return accountNo;
    }

    public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
    }

    public String getCardNo() {
	return cardNo;
    }

    public void setCardNo(String cardNo) {
	this.cardNo = cardNo;
    }

}
