package com.barclays.bmg.ussd.auth.operation.request;

import com.barclays.bmg.context.RequestContext;

public class RetrieveindividualCustCardListOperationRequest extends
		RequestContext {
	private String accountNo;
	private String branchCode;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

}
