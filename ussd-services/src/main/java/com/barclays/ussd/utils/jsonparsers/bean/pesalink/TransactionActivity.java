package com.barclays.ussd.utils.jsonparsers.bean.pesalink;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.bmg.operation.accountdetails.response.CorporateUserAuthDetailsTypeBMG;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionActivity {
	@JsonProperty
	private String transactionRefnbr;
	@JsonProperty
	private String transactionType;
	@JsonProperty
	private String amount;
	@JsonProperty
	private String dateTime;
	@JsonProperty
	private String beneficiaryORBillerName;
	@JsonProperty
	private String toAccount;
	@JsonProperty
	private String undsTransferType;
	@JsonProperty
	private String debitAccountCurrency;
	@JsonProperty
	private String debitAccountNumber;
	@JsonProperty
	private String debitAccountBranch;
	@JsonProperty
	private String debitAccountType;
	@JsonProperty
	private String debitAmount;
	@JsonProperty
	private String customerFullName;
	@JsonProperty
	private String txnBeneficiaryName;
	@JsonProperty
	private String transactionStatus;
	@JsonProperty
	private String transactionRemarks;
	@JsonProperty
	private String authLebel;
	@JsonProperty
	private String initiatedBy;
	@JsonProperty
	private String initiatedDateTime;
	@JsonProperty
	private String authorizedBy;
	@JsonProperty
	private String authorizerName;
	@JsonProperty
	private String authorizedDateTime;
	@JsonProperty
	private String decision;
	@JsonProperty
	private String beneficiaryAcctOrMblno;
	@JsonProperty
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
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
	public String getUndsTransferType() {
		return undsTransferType;
	}
	public void setUndsTransferType(String undsTransferType) {
		this.undsTransferType = undsTransferType;
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
