package com.barclays.bmg.mvc.command.fundtransfer.nonregistered.internal;

public class InternalNonRegisteredFTDetailsCommand {

	private String frActNo;
	private String beneficiaryBranchCode;
	private String beneficiaryAccountNumber;
	private String beneficiaryName;
	private String curr;
	private String txnAmt;
	private String txnDt;

	//private String txnNot;
	private String payDesc;

	public String getFrActNo() {
		return frActNo;
	}
	public void setFrActNo(String frActNo) {
		this.frActNo = frActNo;
	}
	public String getBeneficiaryBranchCode() {
		return beneficiaryBranchCode;
	}
	public void setBeneficiaryBranchCode(String beneficiaryBranchCode) {
		this.beneficiaryBranchCode = beneficiaryBranchCode;
	}
	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}
	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getTxnDt() {
		return txnDt;
	}
	public void setTxnDt(String txnDt) {
		this.txnDt = txnDt;
	}
	public String getPayDesc() {
		return payDesc;
	}
	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

}
