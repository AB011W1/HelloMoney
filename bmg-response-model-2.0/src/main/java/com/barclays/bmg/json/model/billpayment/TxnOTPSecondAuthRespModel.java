package com.barclays.bmg.json.model.billpayment;

import com.barclays.bmg.json.response.BMBPayloadData;


public class TxnOTPSecondAuthRespModel extends BMBPayloadData{

	/**
	 *
	 */
	private static final long serialVersionUID = -7508741913884321473L;
	private String otpPfx;
	private String otpHdrLn1;
	private String otpHdrLn2;
	private String otpFtr;
	private String txnRefNo;

	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
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
