package com.barclays.bmg.mvc.command;


public class ApplyTDSourceAccountDetailsCommand {
	private String actNo;
	private String depositAmount;
	private String productId;
	private String tenureMonths;
	private String tenureDays;

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductId() {
		return productId;
	}

	public void setTenureMonths(String tenureMonth) {
		this.tenureMonths = tenureMonth;
	}

	public String getTenureMonths() {
		return tenureMonths;
	}

	public void setTenureDays(String tenureDays) {
		this.tenureDays = tenureDays;
	}

	public String getTenureDays() {
		return tenureDays;
	}

}