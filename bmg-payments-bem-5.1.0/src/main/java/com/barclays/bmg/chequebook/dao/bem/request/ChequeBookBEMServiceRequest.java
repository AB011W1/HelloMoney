package com.barclays.bmg.chequebook.dao.bem.request;

import com.barclays.bem.AddCheckBookRequest.CheckBook;
import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.BEMServiceHeader.holders.BEMResHeaderHolder;
import com.barclays.bem.TransactionResponseStatus.holders.TransactionResponseStatusHolder;

public class ChequeBookBEMServiceRequest {

	BEMReqHeader bemReqHeader;
	CheckBook checkBook;
	BEMResHeaderHolder bemResHeaderHolder;
	TransactionResponseStatusHolder transactionResponseStatusHolder;

	public BEMReqHeader getBemReqHeader() {
		return bemReqHeader;
	}
	public void setBemReqHeader(BEMReqHeader bemReqHeader) {
		this.bemReqHeader = bemReqHeader;
	}
	public CheckBook getCheckBook() {
		return checkBook;
	}
	public void setCheckBook(CheckBook checkBook) {
		this.checkBook = checkBook;
	}
	public BEMResHeaderHolder getBemResHeaderHolder() {
		return bemResHeaderHolder;
	}
	public void setBemResHeaderHolder(BEMResHeaderHolder bemResHeaderHolder) {
		this.bemResHeaderHolder = bemResHeaderHolder;
	}
	public TransactionResponseStatusHolder getTransactionResponseStatusHolder() {
		return transactionResponseStatusHolder;
	}
	public void setTransactionResponseStatusHolder(
			TransactionResponseStatusHolder transactionResponseStatusHolder) {
		this.transactionResponseStatusHolder = transactionResponseStatusHolder;
	}

}
