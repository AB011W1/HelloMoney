package com.barclays.bmg.mvc.command.billpayment;

public class SearchTransactionHistoryCommand {

	private String transactionType;
	private String groupWalletFlow;
	private String fundsTransferType;
    private String debitAccountNumber;
    private String billerCode;

	public String getGroupWalletFlow() {
		return groupWalletFlow;
	}

	public void setGroupWalletFlow(String groupWalletFlow) {
		this.groupWalletFlow = groupWalletFlow;
	}

	public String getFundsTransferType() {
		return fundsTransferType;
	}

	public void setFundsTransferType(String fundsTransferType) {
		this.fundsTransferType = fundsTransferType;
	}

	public String getDebitAccountNumber() {
		return debitAccountNumber;
	}

	public void setDebitAccountNumber(String debitAccountNumber) {
		this.debitAccountNumber = debitAccountNumber;
	}

	public String getBillerCode() {
		return billerCode;
	}

	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}




}
