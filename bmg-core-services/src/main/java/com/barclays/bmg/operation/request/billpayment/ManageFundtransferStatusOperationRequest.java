package com.barclays.bmg.operation.request.billpayment;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.TransactionDTO;

public class ManageFundtransferStatusOperationRequest extends RequestContext {
	String userId;
	String systemID;
	String transactionNumber;
	String businessID;
	String bankCIF;
	String scvId;
	String creditAmount;
	String debitAmount;
	String initiatingCustomerFullName;
	String transactionStatus;
	String remarks;
	String accno;
	String actionCode;

	TransactionDTO transactionDTO;
	private String billerId;
	private String billerName;
	private String acconoToCredit;
	private String fundTransferType;
	private String transactionStatusMessage;

	public String getTransactionStatusMessage() {
		return transactionStatusMessage;
	}

	public void setTransactionStatusMessage(String transactionStatusMessage) {
		this.transactionStatusMessage = transactionStatusMessage;
	}

	public String getFundTransferType() {
		return fundTransferType;
	}

	public void setFundTransferType(String fundTransferType) {
		this.fundTransferType = fundTransferType;
	}

	public String getAcconoToCredit() {
		return acconoToCredit;
	}

	public void setAcconoToCredit(String acconoToCredit) {
		this.acconoToCredit = acconoToCredit;
	}
	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}
	public TransactionDTO getTransactionDTO() {
		return transactionDTO;
	}

	public void setTransactionDTO(TransactionDTO transactionDTO) {
		this.transactionDTO = transactionDTO;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSystemID() {
		return systemID;
	}

	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getBusinessID() {
		return businessID;
	}

	public void setBusinessID(String businessID) {
		this.businessID = businessID;
	}

	public String getBankCIF() {
		return bankCIF;
	}

	public void setBankCIF(String bankCIF) {
		this.bankCIF = bankCIF;
	}

	public String getScvId() {
		return scvId;
	}

	public void setScvId(String scvId) {
		this.scvId = scvId;
	}

	public String getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}

	public String getInitiatingCustomerFullName() {
		return initiatingCustomerFullName;
	}

	public void setInitiatingCustomerFullName(String initiatingCustomerFullName) {
		this.initiatingCustomerFullName = initiatingCustomerFullName;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
