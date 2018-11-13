package com.barclays.bmg.service.response;

import java.util.Date;

import com.barclays.bmg.context.ResponseContext;

public class PostAuthenticationServiceResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = -6121894066844896438L;
    private boolean success;
    private Date lastLoginTime;

    @Override
    public boolean isSuccess() {
	return success;
    }

    @Override
    public void setSuccess(boolean success) {
	this.success = success;
    }

    public Date getLastLoginTime() {
	return new Date(lastLoginTime.getTime());
    }

    public void setLastLoginTime(Date lastLoginTime) {
	if (lastLoginTime == null) {
	    this.lastLoginTime = null;
	} else {
	    this.lastLoginTime = new Date(lastLoginTime.getTime());
	}
    }
}