package com.barclays.bmg.json.response;

import java.io.Serializable;

public class JSONResponseCodes implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3350698284800445098L;
	public static final String DISPLAY_OTP = "2000";
	public static final String DISPLAY_SQA = "2001";
	public static final String DISPLAY_BP_CNF_SCREEN ="2002";
	public static final String DISPLAY_OWN_FT_CNF_SCREEN ="2003";
	public static final String DISPLAY_LOGIN_SCREEN ="2004";

	private String currScrn;
	private String onCancel;
	public String getCurrScrn() {
		return currScrn;
	}
	public void setCurrScrn(String currScrn) {
		this.currScrn = currScrn;
	}
	public String getOnCancel() {
		return onCancel;
	}
	public void setOnCancel(String onCancel) {
		this.onCancel = onCancel;
	}



}
