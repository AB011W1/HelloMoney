package com.barclays.bmg.mvc.command.billpayment;

public class ExternalFTPayInfoCommand {

	private String frActNo;
	private String payId;

	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getFrActNo() {
		return frActNo;
	}
	public void setFrActNo(String frActNo) {
		this.frActNo = frActNo;
	}


}
