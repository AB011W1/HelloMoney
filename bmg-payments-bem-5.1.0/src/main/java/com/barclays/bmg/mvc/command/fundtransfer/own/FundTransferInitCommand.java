package com.barclays.bmg.mvc.command.fundtransfer.own;

public class FundTransferInitCommand {

	private String CreditCardFlag;

	public void setCreditCardFlag(String creditCardFlag) {
		CreditCardFlag = creditCardFlag;
	}

	public String getCreditCardFlag() {
		return CreditCardFlag;
	}

}
