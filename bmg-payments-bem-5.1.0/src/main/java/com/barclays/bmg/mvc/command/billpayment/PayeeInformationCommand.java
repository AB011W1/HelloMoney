package com.barclays.bmg.mvc.command.billpayment;

public class PayeeInformationCommand {

	private String payId;
	private String paySer;

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public void setPayeeId(String payeeId) {
		this.payId = payeeId;
	}

	public String getPaySer() {
		return paySer;
	}

	public void setPaySer(String paySer) {
		this.paySer = paySer;
	}

}
