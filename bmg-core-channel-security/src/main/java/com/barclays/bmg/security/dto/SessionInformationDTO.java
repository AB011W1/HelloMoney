package com.barclays.bmg.security.dto;

import java.util.Date;

public class SessionInformationDTO {

    private String sessionID;
    private String principalID;
    private Date lastRequestDate;
    private boolean expiredFlag;

    public String getSessionID() {
	return sessionID;
    }

    public void setSessionID(String sessionID) {
	this.sessionID = sessionID;
    }

    public String getPrincipalID() {
	return principalID;
    }

    public void setPrincipalID(String principalID) {
	this.principalID = principalID;
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
