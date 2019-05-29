package com.barclays.bmg.mvc.command.pesalink;

import com.barclays.bmg.dto.TransactionDTO;

public class ManageFundtransferStatusCommand {
	private String userId;
	private String systemID;
	private String transactionNumber;
	private String accono;
	private String bankCIF;
	private String scvId;
	private String creditAmount;
	private String debitAmount;
	private String initiatingCustomerFullName;
	private String transactionStatus;
	private String remarks;
	private String actionCode;
	private TransactionDTO transactionDTO;
	private String billerId;
	private String billerName;
	private String acconoToCredit;
	private String fundTransferType;
	private String transactionStatusMessage;
	private String sendSMS;

	public String getSendSMS() {
		return sendSMS;
	}

	public void setSendSMS(String sendSMS) {
		this.sendSMS = sendSMS;
	}

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

	public String getAccono() {
		return accono;
	}

	public void setAccono(String accono) {
		this.accono = accono;
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
