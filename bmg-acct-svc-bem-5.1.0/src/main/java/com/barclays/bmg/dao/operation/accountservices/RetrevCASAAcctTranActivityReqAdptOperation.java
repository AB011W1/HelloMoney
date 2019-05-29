package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveCASAAcctTransactionActivity.RetrieveCASAAccountTransactionActivityRequest;
import com.barclays.bem.RetrieveNonPersonalCustAccountList.RetrieveNonPersonalCustAccountListRequest;
import com.barclays.bmg.dao.accountservices.adapter.RetrevCASAAcctTranActivityHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.RetrevCASAAcctTranActivityPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetrevCASAAcctTranActivityReqAdptOperation {
	RetrevCASAAcctTranActivityHeaderAdapter retrevCASAAcctTranActivityHeaderAdapter;
	RetrevCASAAcctTranActivityPayloadAdapter retrevCASAAcctTranActivityPayloadAdapter;

	public RetrevCASAAcctTranActivityHeaderAdapter getRetrevCASAAcctTranActivityHeaderAdapter() {
		return retrevCASAAcctTranActivityHeaderAdapter;
	}
	public void setRetrevCASAAcctTranActivityHeaderAdapter(
			RetrevCASAAcctTranActivityHeaderAdapter retrevCASAAcctTranActivityHeaderAdapter) {
		this.retrevCASAAcctTranActivityHeaderAdapter = retrevCASAAcctTranActivityHeaderAdapter;
	}
	public RetrevCASAAcctTranActivityPayloadAdapter getRetrevCASAAcctTranActivityPayloadAdapter() {
		return retrevCASAAcctTranActivityPayloadAdapter;
	}
	public void setRetrevCASAAcctTranActivityPayloadAdapter(
			RetrevCASAAcctTranActivityPayloadAdapter retrevCASAAcctTranActivityPayloadAdapter) {
		this.retrevCASAAcctTranActivityPayloadAdapter = retrevCASAAcctTranActivityPayloadAdapter;
	}
	 //TODO Request type from BEM stub.jar
	public RetrieveCASAAccountTransactionActivityRequest adaptRequest(WorkContext workContext){

		RetrieveCASAAccountTransactionActivityRequest request =new RetrieveCASAAccountTransactionActivityRequest();
		request.setRequestHeader(retrevCASAAcctTranActivityHeaderAdapter.buildRequestHeader(workContext));

		//TODO change implementation of  adaptPayLoad
		request.setCASAAccountTransactionSearchInfo(retrevCASAAcctTranActivityPayloadAdapter.adaptPayLoad(workContext));
		return request;

	}
}
