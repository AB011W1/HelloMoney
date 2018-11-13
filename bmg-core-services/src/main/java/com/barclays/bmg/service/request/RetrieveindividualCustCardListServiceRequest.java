package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class RetrieveindividualCustCardListServiceRequest extends
		RequestContext {
	private String accountNo;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
}
