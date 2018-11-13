package com.barclays.bmg.mvc.command.billpayment;


public class OneTimeBillPayInitCommand {

	private String billerId;
	private String billerType;

	public String getBillerType() {
		return billerType;
	}

	public void setBillerType(String billerType) {
		this.billerType = billerType;
	}

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}



}
