package com.barclays.bmg.mvc.command.billpayment;

public class MTPPayeeInformationCommand {

	private String payId;
	private String paySer;
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getPaySer() {
		return paySer;
	}
	public void setPaySer(String paySer) {
		this.paySer = paySer;
	}

}
