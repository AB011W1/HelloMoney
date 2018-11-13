package com.barclays.bmg.json.model;

import java.io.Serializable;
import java.util.List;

public class SessionSummaryJSONModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6932375304476753444L;

    public static final long ONE_HOUR = 1000 * 60 * 60;
    public static final long ONE_MINUTE = 1000 * 60;
    private String loginTime;
    private String logoutTime;
    private Long duration;
    private String durationHours;
    private String durationMinutes;
    private String durationSeconds;
    private List<SessionActivityJSONModel> sessionActivityList;
    private boolean activityToDisplay= false;


	public boolean isActivityToDisplay() {
		return activityToDisplay;
	}
	public void setActivityToDisplay(boolean activityToDisplay) {
		this.activityToDisplay = activityToDisplay;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public String getDurationHours() {
		return durationHours;
	}
	public void setDurationHours(String durationHours) {
		this.durationHours = durationHours;
	}
	public String getDurationMinutes() {
		return durationMinutes;
	}
	public void setDurationMinutes(String durationMinutes) {
		this.durationMinutes = durationMinutes;
	}
	public String getDurationSeconds() {
		return durationSeconds;
	}
	public void setDurationSeconds(String durationSeconds) {
		this.durationSeconds = durationSeconds;
	}
	public List<SessionActivityJSONModel> getSessionActivityList() {
		return sessionActivityList;
	}
	public void setSessionActivityList(
			List<SessionActivityJSONModel> sessionActivityList) {
		this.sessionActivityList = sessionActivityList;
	}




}
