package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveIndividualCustByCCNumber.RetrieveIndividualCustomerByCCNumberRequest;
import com.barclays.bmg.dao.accountservices.adapter.RetrIndvCustByCCHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.RetrIndvCustByCCPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetrIndvCustByCCReqAdptOperation {

	private RetrIndvCustByCCHeaderAdapter retrIndvCustByCCHeaderAdapter;
	private RetrIndvCustByCCPayloadAdapter retrIndvCustByCCPayloadAdapter;
	public RetrieveIndividualCustomerByCCNumberRequest adaptRequestForIndvPayeeList(WorkContext workContext){

		RetrieveIndividualCustomerByCCNumberRequest request =
						new RetrieveIndividualCustomerByCCNumberRequest();
		request.setRequestHeader(retrIndvCustByCCHeaderAdapter.buildRequestHeader(workContext));
		request.setIndividuaCustomerSearchInfo(retrIndvCustByCCPayloadAdapter.adaptPayload(workContext));

		return request;
	}
	public RetrIndvCustByCCHeaderAdapter getRetrIndvCustByCCHeaderAdapter() {
		return retrIndvCustByCCHeaderAdapter;
	}
	public void setRetrIndvCustByCCHeaderAdapter(
			RetrIndvCustByCCHeaderAdapter retrIndvCustByCCHeaderAdapter) {
		this.retrIndvCustByCCHeaderAdapter = retrIndvCustByCCHeaderAdapter;
	}
	public RetrIndvCustByCCPayloadAdapter getRetrIndvCustByCCPayloadAdapter() {
		return retrIndvCustByCCPayloadAdapter;
	}
	public void setRetrIndvCustByCCPayloadAdapter(
			RetrIndvCustByCCPayloadAdapter retrIndvCustByCCPayloadAdapter) {
		this.retrIndvCustByCCPayloadAdapter = retrIndvCustByCCPayloadAdapter;
	}
}
