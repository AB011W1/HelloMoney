package com.barclays.bmg.mvc.command.billpayment;

public class OneTimeBillPayFormSubmitCommand {

	/**
     * The instance variable named "billerCreditDTO" is created.
     */
	private  String actionCode;
	 /**
     * The instance variable named "billerCreditDTO" is created.
     */
	private  String storeNumber;
    private String billerId;
    private String billerType;
    private String billerRefNumber;
    private String amt;
    private String remarks;
    private String fromActNumber;
    private String currency;
    private String areaCode;
    private String creditCardFlag;
    private String creditcardNo;
    public String getCreditcardNo() {
		return creditcardNo;
	}

	public void setCreditcardNo(String creditcardNo) {
		this.creditcardNo = creditcardNo;
	}

	/**
     * The instance variable named "activityId" is created.
     */
    private String activityId;
    public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getCreditCardFlag() {
		return creditCardFlag;
	}

	public void setCreditCardFlag(String creditCardFlag) {
		this.creditCardFlag = creditCardFlag;
	}

	/**
     * The instance variable named "accountType" is created.
     */
    private String accountType;

    public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public String getAreaCode() {
	return areaCode;
    }

    public void setAreaCode(String areaCode) {
	this.areaCode = areaCode;
    }

    public String getBillerId() {
	return billerId;
    }

    public void setBillerId(String billerId) {
	this.billerId = billerId;
    }

    public String getBillerType() {
	return billerType;
    }

    public void setBillerType(String billerType) {
	this.billerType = billerType;
    }

    public String getBillerRefNumber() {
	return billerRefNumber;
    }

    public void setBillerRefNumber(String billerRefNumber) {
	this.billerRefNumber = billerRefNumber;
    }

    public String getAmt() {
	return amt;
    }

    public void setAmt(String amt) {
	this.amt = amt;
    }

    public String getRemarks() {
	return remarks;
    }

    public void setRemarks(String remarks) {
	this.remarks = remarks;
    }

    public String getFromActNumber() {
	return fromActNumber;
    }

    public void setFromActNumber(String fromActNumber) {
	this.fromActNumber = fromActNumber;
    }

    public String getCurrency() {
	return currency;
    }

    public void setCurrency(String currency) {
	this.currency = currency;
    }

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

}
