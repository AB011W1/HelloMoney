package com.barclays.bmg.service.response.bankdraft;

import java.util.Date;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BankDraftTransactionDTO;

public class PurchaseBankDraftServiceResponse extends ResponseContext {

    private static final long serialVersionUID = -60151803370547935L;

    private String transactionRefNo;
    private Date transactionDate;
    private Date bemTransactionDate;
    private String bemTransactionRefNo;
    private String txnMsg;

    private BankDraftTransactionDTO bankDraftTransactionDTO;

    public String getTransactionRefNo() {
	return transactionRefNo;
    }

    public void setTransactionRefNo(String transactionRefNo) {
	this.transactionRefNo = transactionRefNo;
    }

    public BankDraftTransactionDTO getBankDraftTransactionDTO() {
	return bankDraftTransactionDTO;
    }

    public void setBankDraftTransactionDTO(BankDraftTransactionDTO bankDraftTransactionDTO) {
	this.bankDraftTransactionDTO = bankDraftTransactionDTO;
    }

    public Date getTransactionDate() {
	return new Date(transactionDate.getTime());
    }

    public void setTransactionDate(Date transactionDate) {
	if (transactionDate == null) {
	    this.transactionDate = null;
	} else {
	    this.transactionDate = new Date(transactionDate.getTime());
	}
    }

    public Date getBemTransactionDate() {
	return new Date(bemTransactionDate.getTime());
    }

    public void setBemTransactionDate(Date bemTransactionDate) {
	if (bemTransactionDate == null) {
	    this.bemTransactionDate = null;
	} else {
	    this.bemTransactionDate = new Date(bemTransactionDate.getTime());
	}
    }

    public String getBemTransactionRefNo() {
	return bemTransactionRefNo;
    }

    public void setBemTransactionRefNo(String bemTransactionRefNo) {
	this.bemTransactionRefNo = bemTransactionRefNo;
    }

    public String getTxnMsg() {
	return txnMsg;
    }

    public void setTxnMsg(String txnMsg) {
	this.txnMsg = txnMsg;
    }

}
