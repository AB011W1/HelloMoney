package com.barclays.bmg.operation.accountdetails.response;

import java.util.List;

public class TransactionActivityService {
	private String transactionRefnbr;
	private String transactionType;
	private String Amount;
	private String dateTime;
	private String beneficiaryORBillerName;
	private String toAccount;
	private String totalRecords;
	private String fundsTransferType;
	private String debitAccountCurrency;
	private String debitAccountNumber;
	private String debitAccountBranch;
	private String debitAccountType;
	private String debitAmount;
	private String customerFullName;
	private String txnBeneficiaryName;
	private String transactionStatus;
	private String transactionRemarks;
	private String authLebel;
	private String initiatedBy;
	private String initiatedDateTime;
	private String authorizedBy;
	private String authorizerName;
	private String authorizedDateTime;
	private String decision;
	private String remarks;
	private String beneficiaryAcctOrMblno;
	private List<CorporateUserAuthDetailsTypeBMG> corporateUserAuthDetailsType;

	public List<CorporateUserAuthDetailsTypeBMG> getCorporateUserAuthDetailsType() {
		return corporateUserAuthDetailsType;
	}

	public void setCorporateUserAuthDetailsType(
			List<CorporateUserAuthDetailsTypeBMG> corporateUserAuthDetailsType) {
		this.corporateUserAuthDetailsType = corporateUserAuthDetailsType;
	}

	public String getBeneficiaryAcctOrMblno() {
		return beneficiaryAcctOrMblno;
	}

	public void setBeneficiaryAcctOrMblno(String beneficiaryAcctOrMblno) {
		this.beneficiaryAcctOrMblno = beneficiaryAcctOrMblno;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTransactionRefnbr() {
		return transactionRefnbr;
	}

	public void setTransactionRefnbr(String transactionRefnbr) {
		this.transactionRefnbr = transactionRefnbr;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getBeneficiaryORBillerName() {
		return beneficiaryORBillerName;
	}

	public void setBeneficiaryORBillerName(String beneficiaryORBillerName) {
		this.beneficiaryORBillerName = beneficiaryORBillerName;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public String getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getFundsTransferType() {
		return fundsTransferType;
	}

	public void setFundsTransferType(String fundsTransferType) {
		this.fundsTransferType = fundsTransferType;
	}

	public String getDebitAccountCurrency() {
		return debitAccountCurrency;
	}

	public void setDebitAccountCurrency(String debitAccountCurrency) {
		this.debitAccountCurrency = debitAccountCurrency;
	}

	public String getDebitAccountNumber() {
		return debitAccountNumber;
	}

	public void setDebitAccountNumber(String debitAccountNumber) {
		this.debitAccountNumber = debitAccountNumber;
	}

	public String getDebitAccountBranch() {
		return debitAccountBranch;
	}

	public void setDebitAccountBranch(String debitAccountBranch) {
		this.debitAccountBranch = debitAccountBranch;
	}

	public String getDebitAccountType() {
		return debitAccountType;
	}

	public void setDebitAccountType(String debitAccountType) {
		this.debitAccountType = debitAccountType;
	}

	public String getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public String getTxnBeneficiaryName() {
		return txnBeneficiaryName;
	}

	public void setTxnBeneficiaryName(String txnBeneficiaryName) {
		this.txnBeneficiaryName = txnBeneficiaryName;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getTransactionRemarks() {
		return transactionRemarks;
	}

	public void setTransactionRemarks(String transactionRemarks) {
		this.transactionRemarks = transactionRemarks;
	}

	public String getAuthLebel() {
		return authLebel;
	}

	public void setAuthLebel(String authLebel) {
		this.authLebel = authLebel;
	}

	public String getInitiatedBy() {
		return initiatedBy;
	}

	public void setInitiatedBy(String initiatedBy) {
		this.initiatedBy = initiatedBy;
	}

	public String getInitiatedDateTime() {
		return initiatedDateTime;
	}

	public void setInitiatedDateTime(String initiatedDateTime) {
		this.initiatedDateTime = initiatedDateTime;
	}

	public String getAuthorizedBy() {
		return authorizedBy;
	}

	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
	}

	public String getAuthorizerName() {
		return authorizerName;
	}

	public void setAuthorizerName(String authorizerName) {
		this.authorizerName = authorizerName;
	}

	public String getAuthorizedDateTime() {
		return authorizedDateTime;
	}

	public void setAuthorizedDateTime(String authorizedDateTime) {
		this.authorizedDateTime = authorizedDateTime;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}
}
