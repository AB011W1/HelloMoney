package com.barclays.bmg.service.accounts.request;

import java.util.List;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.operation.accountdetails.response.CorporateUserAuthDetailsTypeBMG;

public class RetrevCASAAcctTranActivityOperationRequest extends RequestContext {
	private String userID;
	private String accountNumber;
	private String branchCode;
	private String transactionStatus;
	private String trxReferenceNumber;
	private String lockRequired;
	private String actionCode;
	private String tranTypeCode;

	public String getTranTypeCode() {
		return tranTypeCode;
	}

	public void setTranTypeCode(String tranTypeCode) {
		this.tranTypeCode = tranTypeCode;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getTrxReferenceNumber() {
		return trxReferenceNumber;
	}

	public void setTrxReferenceNumber(String trxReferenceNumber) {
		this.trxReferenceNumber = trxReferenceNumber;
	}

	public String getLockRequired() {
		return lockRequired;
	}

	public void setLockRequired(String lockRequired) {
		this.lockRequired = lockRequired;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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
