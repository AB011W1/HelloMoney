package com.barclays.bmg.service.request.USSDAuthentication;

import com.barclays.bmg.context.RequestContext;

public class GetChangeRecordsRequest extends RequestContext{

	private String accountNumber;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
