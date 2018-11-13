package com.barclays.bmg.operation.accounts.request;


import com.barclays.bmg.context.ResponseContext;

public class ApplyProductConfirmOperationResponse extends ResponseContext {

	private static final long serialVersionUID = 6017509621703548165L;
	private String caseNumber;

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getCaseNumber() {
		return caseNumber;
	}
}
