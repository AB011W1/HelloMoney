package com.barclays.bmg.operation.response;

import java.util.Date;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.utils.BMGFormatUtility;

public class PostAuthenticationOperationResponse extends ResponseContext {

    private String lastLoginTime;
    private String secondAuthResId;

    public String getLastLoginTime() {
	return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
	// this.lastLoginTime = lastLoginTime;
	this.lastLoginTime = BMGFormatUtility.getLongDate(lastLoginTime);
    }

    public String getSecondAuthResId() {
	return secondAuthResId;
    }

    public void setSecondAuthResId(String secondAuthResId) {
	this.secondAuthResId = secondAuthResId;
    }

}
