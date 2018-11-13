package com.barclays.bmg.json.model.auth;


import com.barclays.bmg.json.response.BMBPayloadData;

public class OTPResponseModel extends BMBPayloadData {

//	private String otpPrefix;
//	private String otpHeaderLine1;
//	private String otpHeaderLine2;
//	private String otpFooter;


	private static final long serialVersionUID = 4077552875198161467L;
	private String otpPfx;
	private String otpHdrLn1;
	private String otpHdrLn2;
	private String otpFtr;

	public String getOtpPfx() {
		return otpPfx;
	}
	public void setOtpPfx(String otpPfx) {
		this.otpPfx = otpPfx;
	}
	public String getOtpHdrLn1() {
		return otpHdrLn1;
	}
	public void setOtpHdrLn1(String otpHdrLn1) {
		this.otpHdrLn1 = otpHdrLn1;
	}
	public String getOtpHdrLn2() {
		return otpHdrLn2;
	}
	public void setOtpHdrLn2(String otpHdrLn2) {
		this.otpHdrLn2 = otpHdrLn2;
	}
	public String getOtpFtr() {
		return otpFtr;
	}
	public void setOtpFtr(String otpFtr) {
		this.otpFtr = otpFtr;
	}


}
