package com.barclays.bmg.json.model.auth;

import com.barclays.bmg.json.response.BMBPayloadData;



public class SQAResponseModel extends BMBPayloadData {

/**
	 *
	 */
	private static final long serialVersionUID = -6998589164144482789L;
//	private String secondFactorAuthType;
	private String sqaQues;





	public SQAResponseModel() {
		super();
//		this.secondFactorAuthType = "OTP";
		this.sqaQues = "";
	}





	public String getSqaQues() {
		return sqaQues;
	}





	public void setSqaQues(String sqaQues) {
		this.sqaQues = sqaQues;
	}



//	public String getSecondFactorAuthType() {
//		return secondFactorAuthType;
//	}
//
//	public void setSecondFactorAuthType(String secondFactor) {
//		this.secondFactorAuthType = secondFactor;
//	}

}
