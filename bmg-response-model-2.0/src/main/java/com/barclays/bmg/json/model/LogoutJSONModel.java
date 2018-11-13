package com.barclays.bmg.json.model;

import java.util.List;

import com.barclays.bmg.json.response.BMBPayloadData;


public class LogoutJSONModel extends BMBPayloadData {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	//private SessionSummaryJSONModel sessionSummaryJSONModel;


   public static final long ONE_HOUR = 1000 * 60 * 60;
   public static final long ONE_MINUTE = 1000 * 60;
   private String lgnTm;
   private String lgtTm;
   private Long dur;
   private String durHrs;
   private String durMins;
   private String durSecs;
   private List<SessionActivityJSONModel> ssnActvLst;
   private boolean actvToDis= false;


	public String getLgnTm() {
		return lgnTm;
	}
	public void setLgnTm(String lgnTm) {
		this.lgnTm = lgnTm;
		}
	public String getLgtTm() {
		return lgtTm;
	}
	public void setLgtTm(String lgtTm) {
		this.lgtTm = lgtTm;
	}
	public Long getDur() {
		return dur;
	}
	public void setDur(Long dur) {
		this.dur = dur;
	}
	public String getDurHrs() {
		return durHrs;
	}
	public void setDurHrs(String durHrs) {
		this.durHrs = durHrs;
	}
	public String getDurMins() {
		return durMins;
	}
	public void setDurMins(String durMins) {
		this.durMins = durMins;
	}
	public String getDurSecs() {
		return durSecs;
	}
	public void setDurSecs(String durSecs) {
		this.durSecs = durSecs;
	}
	public List<SessionActivityJSONModel> getSsnActvLst() {
		return ssnActvLst;
	}
	public void setSsnActvLst(List<SessionActivityJSONModel> ssnActvLst) {
		this.ssnActvLst = ssnActvLst;
	}
	public boolean isActvToDis() {
		return actvToDis;
	}
	public void setActvToDis(boolean actvToDis) {
		this.actvToDis = actvToDis;
	}



	/*public SessionSummaryJSONModel getSessionSummaryJSONModel() {
		return sessionSummaryJSONModel;
	}

	public void setSessionSummaryJSONModel(
			SessionSummaryJSONModel sessionSummaryJSONModel) {
		this.sessionSummaryJSONModel = sessionSummaryJSONModel;
	}*/

}
