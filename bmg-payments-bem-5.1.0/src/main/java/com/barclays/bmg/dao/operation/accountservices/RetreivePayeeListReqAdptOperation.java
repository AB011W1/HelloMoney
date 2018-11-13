package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveIndividualCustBeneficiaryList.RetrieveIndividualCustomerBeneficiaryListRequest;
import com.barclays.bmg.dao.accountservices.adapter.RetreiveIndvPayeeListHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.RetreiveIndvPayeeListPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetreivePayeeListReqAdptOperation {

	private RetreiveIndvPayeeListHeaderAdapter retreiveIndvPayeeListHeaderAdapter;
	private RetreiveIndvPayeeListPayloadAdapter retreiveIndvPayeeListPayloadAdapter;

	public RetrieveIndividualCustomerBeneficiaryListRequest adaptRequestForIndvPayeeList(WorkContext workContext){

		RetrieveIndividualCustomerBeneficiaryListRequest request =
					new RetrieveIndividualCustomerBeneficiaryListRequest();
		request.setRequestHeader(retreiveIndvPayeeListHeaderAdapter.buildRequestHeader(workContext));
		request.setIndividualCustomerBeneficiaryInfo(retreiveIndvPayeeListPayloadAdapter.adaptPayLoad(workContext));
		return request;
	}

	public RetreiveIndvPayeeListHeaderAdapter getRetreiveIndvPayeeListHeaderAdapter() {
		return retreiveIndvPayeeListHeaderAdapter;
	}

	public void setRetreiveIndvPayeeListHeaderAdapter(
			RetreiveIndvPayeeListHeaderAdapter retreiveIndvPayeeListHeaderAdapter) {
		this.retreiveIndvPayeeListHeaderAdapter = retreiveIndvPayeeListHeaderAdapter;
	}

	public RetreiveIndvPayeeListPayloadAdapter getRetreiveIndvPayeeListPayloadAdapter() {
		return retreiveIndvPayeeListPayloadAdapter;
	}

	public void setRetreiveIndvPayeeListPayloadAdapter(
			RetreiveIndvPayeeListPayloadAdapter retreiveIndvPayeeListPayloadAdapter) {
		this.retreiveIndvPayeeListPayloadAdapter = retreiveIndvPayeeListPayloadAdapter;
	}
}
