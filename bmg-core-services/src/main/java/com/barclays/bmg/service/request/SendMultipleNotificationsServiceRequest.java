package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class SendMultipleNotificationsServiceRequest extends RequestContext {
	private String accountNo;
	private String transId;
	private String amount;
	private String userRefno;
	private String eventId;
	private String[] mobileNo;
	private String priority;
	private String dynamicFields;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUserRefno() {
		return userRefno;
	}

	public void setUserRefno(String userRefno) {
		this.userRefno = userRefno;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String[] getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String[] mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPriority() {
		// TODO Auto-generated method stub
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDynamicFields() {
		return dynamicFields;
	}

	public void setDynamicFields(String dynamicFields) {
		this.dynamicFields = dynamicFields;
	}
}
