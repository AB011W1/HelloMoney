package com.barclays.bmg.mvc.command.pesalink;

public class RetrieveCASAAcctTranActivityCommand {
	private String accNo;
	private String branchCode;
	private String transactionStatus;
	private String actionCode;
	private String transactionNumber;
	private String lockRequired;
	private String tranTypeCode;

	public String getTranTypeCode() {
		return tranTypeCode;
	}

	public void setTranTypeCode(String tranTypeCode) {
		this.tranTypeCode = tranTypeCode;
	}

	public String getLockRequired() {
		return lockRequired;
	}

	public void setLockRequired(String lockRequired) {
		this.lockRequired = lockRequired;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

}
