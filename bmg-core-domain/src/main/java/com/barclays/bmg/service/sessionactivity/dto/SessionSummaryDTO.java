package com.barclays.bmg.service.sessionactivity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SessionSummaryDTO implements Serializable {
    /**
	 *
	 */
    private static final long serialVersionUID = -4077052361203156357L;
    public static final long ONE_HOUR = 1000 * 60 * 60;
    public static final long ONE_MINUTE = 1000 * 60;
    private Date loginTime;
    private Date logoutTime;
    private Long duration;
    private String durationHours;
    private String durationMinutes;
    private String durationSeconds;
    private String sessionId;
    private List<SessionActivityDTO> sessionActivityList;

    /**
     * @return the loginTime
     */
    public Date getLoginTime() {
	if (loginTime != null) {
	    return new Date(loginTime.getTime());
	}
	return null;
    }

    /**
     * @param loginTime
     *            the loginTime to set
     */
    public void setLoginTime(Date loginTime) {
	if (loginTime != null) {
	    this.loginTime = new Date(loginTime.getTime());
	} else {
	    this.loginTime = null;
	}
    }

    /**
     * @return the logoutTime
     */
    public Date getLogoutTime() {
	if (logoutTime != null) {
	    return new Date(logoutTime.getTime());
	}
	return null;
    }

    /**
     * @param logoutTime
     *            the logoutTime to set
     */
    public void setLogoutTime(Date logoutTime) {
	if (logoutTime != null) {
	    this.logoutTime = new Date(logoutTime.getTime());
	} else {
	    this.logoutTime = null;
	}
    }

    /**
     * @return the duration
     */
    public Long getDuration() {
	return duration;
    }

    /**
     * @param duration
     *            the duration to set
     */
    public void setDuration(Long duration) {
	this.duration = duration;
    }

    /**
     * @return the durationHours
     */
    public String getDurationHours() {
	return durationHours;
    }

    /**
     * @param durationHours
     *            the durationHours to set
     */
    public void setDurationHours(String durationHours) {
	this.durationHours = durationHours;
    }

    /**
     * @return the durationMinutes
     */
    public String getDurationMinutes() {
	return durationMinutes;
    }

    /**
     * @param durationMinutes
     *            the durationMinutes to set
     */
    public void setDurationMinutes(String durationMinutes) {
	this.durationMinutes = durationMinutes;
    }

    /**
     * @return the durationSeconds
     */
    public String getDurationSeconds() {
	return durationSeconds;
    }

    /**
     * @param durationSeconds
     *            the durationSeconds to set
     */
    public void setDurationSeconds(String durationSeconds) {
	this.durationSeconds = durationSeconds;
    }

    /**
     * @return the session id
     */
    public String getSessionId() {
	return sessionId;
    }

    /**
     * @param sessionId
     *            the session id to set
     */
    public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
    }

    /**
     * @return the sessionActivityList
     */
    public List<SessionActivityDTO> getSessionActivityList() {
	return sessionActivityList;
    }

    /**
     * @param sessionActivityList
     *            the sessionActivityList to set
     */
    public void setSessionActivityList(List<SessionActivityDTO> sessionActivityList) {
	this.sessionActivityList = sessionActivityList;
    }
}
