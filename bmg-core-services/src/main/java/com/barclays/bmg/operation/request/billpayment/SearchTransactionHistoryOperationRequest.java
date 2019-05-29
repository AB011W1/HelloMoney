package com.barclays.bmg.operation.request.billpayment;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.TransactionHistoryDTO;

public class SearchTransactionHistoryOperationRequest extends RequestContext {

	private TransactionHistoryDTO transactionHistoryDTO;
	private String isGroupWalletFlow;
	private String fundsTransferType;
    private String debitAccountNumber;
    private String billerCode;

    public TransactionHistoryDTO getTransactionHistoryDTO() {
	return transactionHistoryDTO;
    }

    public void setTransactionHistoryDTO(TransactionHistoryDTO transactionHistoryDTO) {
	this.transactionHistoryDTO = transactionHistoryDTO;
    }
	public String isGroupWalletFlow() {
		return isGroupWalletFlow;
	}

	public void setGroupWalletFlow(String isGroupWalletFlow) {
		this.isGroupWalletFlow = isGroupWalletFlow;
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
}
