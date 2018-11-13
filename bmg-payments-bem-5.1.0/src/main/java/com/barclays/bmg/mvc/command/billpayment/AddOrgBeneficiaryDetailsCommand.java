package com.barclays.bmg.mvc.command.billpayment;

public class AddOrgBeneficiaryDetailsCommand {

	private String payId;

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}


	private String billerId;
	private String billerType;
	private String billerAccNum;
	private String  referenceNum;
	private String  billerNickName;
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
	public String getReferenceNum() {
		return referenceNum;
	}
	public void setReferenceNum(String referenceNum) {
		this.referenceNum = referenceNum;
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
	private String  billerAddress;


}
