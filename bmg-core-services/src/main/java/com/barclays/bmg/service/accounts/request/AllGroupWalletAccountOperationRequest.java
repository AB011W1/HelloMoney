package com.barclays.bmg.service.accounts.request;

import com.barclays.bmg.context.RequestContext;

public class AllGroupWalletAccountOperationRequest extends RequestContext {
	private String customerID;
	private String customerType;
	private String actionCode;
	private String actionCodeValue;
	private String accountNumber;

	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getActionCodeValue() {
		return actionCodeValue;
	}
	public void setActionCodeValue(String actionCodeValue) {
		this.actionCodeValue = actionCodeValue;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
