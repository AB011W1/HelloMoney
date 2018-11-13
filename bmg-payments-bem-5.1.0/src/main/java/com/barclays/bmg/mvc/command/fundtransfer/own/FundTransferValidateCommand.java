package com.barclays.bmg.mvc.command.fundtransfer.own;

import com.barclays.bmg.utils.BMBCommonUtility;

public class FundTransferValidateCommand {


	private String frActNo;
	private String toActNo;
	private String txnAmt;
	private String txnDt;
	private String txnNot;
	private String identifier;
	private String accountType;
	private String activityId;


	public String getFrActNo() {
		return frActNo;
	}
	public void setFrActNo(String frActNo) {
		this.frActNo = frActNo;
	}
	public void setFromAccountNo(String fromAccountNo) {
		this.frActNo = fromAccountNo;
	}

	public String getToActNo() {
		return toActNo;
	}
	public void setToActNo(String toActNo) {
		this.toActNo = toActNo;
	}
	public void setToAccountNo(String toAccountNo) {
		this.toActNo = toAccountNo;
	}

	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public void setTxAmount(String txAmount) {
		this.txnAmt = txAmount;
	}

	public String getTxnDt() {
		return txnDt;
	}
	public void setTxnDt(String txnDt) {
		this.txnDt = txnDt;
	}
	public void setTxDate(String txDate) {
		this.txnDt = txDate;
	}

	public String getTxnNot() {
		txnNot =	BMBCommonUtility.convertStringToUnicode(txnNot);
		return txnNot;
	}
	public void setTxnNot(String txnNot) {
		this.txnNot = txnNot;
	}
	public void setTxNote(String txNote) {
		this.txnNot = txNote;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}




}
