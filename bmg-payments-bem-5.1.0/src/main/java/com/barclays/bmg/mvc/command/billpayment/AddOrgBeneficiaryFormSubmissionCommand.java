package com.barclays.bmg.mvc.command.billpayment;

public class AddOrgBeneficiaryFormSubmissionCommand {
	// Inputs to be read from uSSD for Validation controller
	private String billerId;
	private String billerNickName;
	private String billerReferenceNum;
	private String billerType;
	private String billerAccNum;

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

	public String getBillerType() {
		return billerType;
	}

	public void setBillerType(String billerType) {
		this.billerType = billerType;
	}

	public String getBillerAccNum() {
		return billerAccNum;
	}

	public void setBillerAccNum(String billerAccNum) {
		this.billerAccNum = billerAccNum;
	}

	public String getBillerNickName() {
		return billerNickName;
	}

	public void setBillerNickName(String billerNickName) {
		this.billerNickName = billerNickName;
	}

	public String getBillerAddress() {
		return billerAddress;
	}

	public void setBillerAddress(String billerAddress) {
		this.billerAddress = billerAddress;
	}

	public void setBillerReferenceNum(String billerReferenceNum) {
		this.billerReferenceNum = billerReferenceNum;
	}

	public String getBillerReferenceNum() {
		return billerReferenceNum;
	}

	private String billerAddress;

}
