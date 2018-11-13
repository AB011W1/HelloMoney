package com.barclays.bmg.mvc.command.addprospect;

public class AddProspectCommand {

    private String accountType;
    private String activityId;

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

}
