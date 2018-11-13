package com.barclays.bmg.mvc.operation.response.bankdraft;

import java.util.Date;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BankDraftTransactionDTO;

public class PurchaseBankDraftOperationResponse extends ResponseContext {

	private static final long serialVersionUID = 2797212804082241104L;

	private String transactionRefNo;
	private Date transactionDate;
	private String txnMsg;
	private BankDraftTransactionDTO bankDraftTransactionDTO;

	public String getTransactionRefNo() {
		return transactionRefNo;
	}

	public void setTransactionRefNo(String transactionRefNo) {
		this.transactionRefNo = transactionRefNo;
	}

	public Date getTransactionDate() {
		return (Date) transactionDate.clone();
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = (Date) transactionDate.clone();
	}

	public BankDraftTransactionDTO getBankDraftTransactionDTO() {
		return bankDraftTransactionDTO;
	}

	public void setBankDraftTransactionDTO(
			BankDraftTransactionDTO bankDraftTransactionDTO) {
		this.bankDraftTransactionDTO = bankDraftTransactionDTO;
	}

	public String getTxnMsg() {
		return txnMsg;
	}

	public void setTxnMsg(String txnMsg) {
		this.txnMsg = txnMsg;
	}

}
