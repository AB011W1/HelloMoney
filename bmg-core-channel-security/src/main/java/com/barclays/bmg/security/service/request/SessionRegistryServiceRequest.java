package com.barclays.bmg.security.service.request;

import java.util.Date;

import com.barclays.bmg.context.RequestContext;

public class SessionRegistryServiceRequest extends RequestContext {

    String principalID;
    String SessionID;
    private Date lastRequestDate;
    private boolean expiredFlag;

    public String getPrincipalID() {
	return principalID;
    }

    public void setPrincipalID(String principalID) {
	this.principalID = principalID;
    }

    public String getSessionID() {
	return SessionID;
    }

    public void setSessionID(String sessionID) {
	SessionID = sessionID;
    }

    public Date getLastRequestDate() {
	return (Date) lastRequestDate.clone();
    }

    public void setLastRequestDate(Date lastRequestDate) {
	this.lastRequestDate = (Date) lastRequestDate.clone();
    }

    public boolean isExpiredFlag() {
	return expiredFlag;
    }

    public void setExpiredFlag(boolean expiredFlag) {
	this.expiredFlag = expiredFlag;
    }
}
