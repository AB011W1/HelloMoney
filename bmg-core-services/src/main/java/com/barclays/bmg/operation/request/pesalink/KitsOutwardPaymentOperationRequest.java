package com.barclays.bmg.operation.request.pesalink;

import com.barclays.bmg.context.RequestContext;

public class KitsOutwardPaymentOperationRequest extends RequestContext {


	private String debitAccount;
	private String txnReason;
	private String txnAmount;
	private String selectedBank;
	private String selectedBankSortCode;
	private String receipientAccountNo;

	public String getDebitAccount() {
		return debitAccount;
	}
	public void setDebitAccount(String debitAccount) {
		this.debitAccount = debitAccount;
	}
	public String getTxnReason() {
		return txnReason;
	}
	public void setTxnReason(String txnReason) {
		this.txnReason = txnReason;
	}
	public String getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getSelectedBank() {
		return selectedBank;
	}
	public void setSelectedBank(String selectedBank) {
		this.selectedBank = selectedBank;
	}
	public String getSelectedBankSortCode() {
		return selectedBankSortCode;
	}
	public void setSelectedBankSortCode(String selectedBankSortCode) {
		this.selectedBankSortCode = selectedBankSortCode;
	}
	public String getReceipientAccountNo() {
		return receipientAccountNo;
	}
	public void setReceipientAccountNo(String receipientAccountNo) {
		this.receipientAccountNo = receipientAccountNo;
	}



}
