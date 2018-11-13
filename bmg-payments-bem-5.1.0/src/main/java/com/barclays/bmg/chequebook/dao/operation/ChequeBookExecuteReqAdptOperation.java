package com.barclays.bmg.chequebook.dao.operation;

import com.barclays.bem.BEMServiceHeader.holders.BEMResHeaderHolder;
import com.barclays.bem.TransactionResponseStatus.holders.TransactionResponseStatusHolder;
import com.barclays.bmg.chequebook.dao.adapter.ChequeBookExecutePayloadAdapter;
import com.barclays.bmg.chequebook.dao.adapter.ChequeBookRequestHeaderAdapter;
import com.barclays.bmg.chequebook.dao.bem.request.ChequeBookBEMServiceRequest;
import com.barclays.bmg.dao.core.context.WorkContext;

public class ChequeBookExecuteReqAdptOperation {

	ChequeBookRequestHeaderAdapter chequeBookRequestHeaderAdapter;
	ChequeBookExecutePayloadAdapter chequeBookExecutePayloadAdapter;

	public ChequeBookBEMServiceRequest adaptRequest(WorkContext workContext){
		ChequeBookBEMServiceRequest chequeBookBEMServiceRequest =
					new ChequeBookBEMServiceRequest();

		chequeBookBEMServiceRequest.setBemReqHeader(chequeBookRequestHeaderAdapter.buildRequestHeader(workContext));
		chequeBookBEMServiceRequest.setCheckBook(chequeBookExecutePayloadAdapter.adaptPayLoad(workContext));
		chequeBookBEMServiceRequest.setBemResHeaderHolder(new BEMResHeaderHolder());
		chequeBookBEMServiceRequest.setTransactionResponseStatusHolder(new TransactionResponseStatusHolder());

		return chequeBookBEMServiceRequest;


	}
	public ChequeBookRequestHeaderAdapter getChequeBookRequestHeaderAdapter() {
		return chequeBookRequestHeaderAdapter;
	}
	public void setChequeBookRequestHeaderAdapter(
			ChequeBookRequestHeaderAdapter chequeBookRequestHeaderAdapter) {
		this.chequeBookRequestHeaderAdapter = chequeBookRequestHeaderAdapter;
	}
	public ChequeBookExecutePayloadAdapter getChequeBookExecutePayloadAdapter() {
		return chequeBookExecutePayloadAdapter;
	}
	public void setChequeBookExecutePayloadAdapter(
			ChequeBookExecutePayloadAdapter chequeBookExecutePayloadAdapter) {
		this.chequeBookExecutePayloadAdapter = chequeBookExecutePayloadAdapter;
	}

}
