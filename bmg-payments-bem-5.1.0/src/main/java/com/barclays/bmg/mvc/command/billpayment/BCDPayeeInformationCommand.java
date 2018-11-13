package com.barclays.bmg.mvc.command.billpayment;

public class BCDPayeeInformationCommand {

	private String payId;
	private String toActNo;
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getToActNo() {
		return toActNo;
	}
	public void setToActNo(String toActNo) {
		this.toActNo = toActNo;
	}
}
