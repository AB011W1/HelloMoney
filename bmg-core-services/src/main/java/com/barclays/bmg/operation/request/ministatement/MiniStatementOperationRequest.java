package com.barclays.bmg.operation.request.ministatement;

import com.barclays.bmg.context.RequestContext;

public class MiniStatementOperationRequest extends RequestContext {

    private String accountNumber;

    public String getAccountNumber() {
	return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
    }

}
