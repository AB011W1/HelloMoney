package com.barclays.bmg.mvc.command.accountdetails;

import java.util.Date;

public class CreditCardActivityTransCommand {

	private String actNo;
	private String activityDate;
	private String currency;

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}