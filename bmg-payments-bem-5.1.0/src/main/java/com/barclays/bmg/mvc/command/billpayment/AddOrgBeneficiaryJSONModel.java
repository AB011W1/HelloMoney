package com.barclays.bmg.mvc.command.billpayment;

import java.io.Serializable;

import com.barclays.bmg.json.response.BaseJSONResponseModel;

public class AddOrgBeneficiaryJSONModel extends BaseJSONResponseModel implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private String billerID;

	// This is the label that will get displayed on the screen
	private String displayLabel;

	private String payeeId;

	private String accountNumber;

	public String getBillerID() {
		return billerID;
	}

	public void setBillerID(String billerID) {
		this.billerID = billerID;
	}

	public String getDisplayLabel() {
		return displayLabel;
	}

	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}

	public String getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(String payeeId) {
		this.payeeId = payeeId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}
