package com.barclays.bmg.mvc.command.billpayment;

public class PayInfoCommandRel1 {

	private String payId;
	private String paySer;
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
	public String getPaySer() {
		return paySer;
	}
	public void setPaySer(String paySer) {
		this.paySer = paySer;
	}

}
