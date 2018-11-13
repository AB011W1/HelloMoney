package com.barclays.bmg.json.model;

import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.json.response.BMBPayloadData;

public class CashSendOneTimeValidateJSONModel extends BMBPayloadData {

	private static final long serialVersionUID = -6783288303236568391L;
	private CasaAccountJSONModel frmAct;
	private AmountJSONModel txnAmt;
	private String mobileNo = "";

	// CPB change 18/04/2017
	private AmountJSONModel transactionFeeAmount;
	private Double taxAmount;

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	private String txnRefNo = "";

	public AmountJSONModel getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(AmountJSONModel txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	public CasaAccountJSONModel getFrmAct() {
		return frmAct;
	}

	public void setFrmAct(CasaAccountJSONModel frmAct) {
		this.frmAct = frmAct;
	}

	public AmountJSONModel getTransactionFeeAmount() {
		return transactionFeeAmount;
	}

	public void setTransactionFeeAmount(AmountJSONModel transactionFeeAmount) {
		this.transactionFeeAmount = transactionFeeAmount;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

}
