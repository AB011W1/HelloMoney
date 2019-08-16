package com.barclays.bmg.mvc.command.accountdetails;

public class AtAGlanceCommand {

    private String accountType;
    private String activityId;
    private String payId;
    private String crditCardFlg;

    public String getAccountType() {
	return accountType;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public void setAccountType(String accountType) {
	this.accountType = accountType;
    }

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getCrditCardFlg() {
		return crditCardFlg;
	}

	public void setCrditCardFlg(String crditCardFlg) {
		this.crditCardFlg = crditCardFlg;
	}


}
