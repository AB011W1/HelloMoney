package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.AddIndividualBeneficiary.AddIndividualBeneficiaryRequest;
import com.barclays.bmg.dao.accountservices.adapter.AddBeneficiaryHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.AddBeneficiaryPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class AddBeneficiaryReqAdptOperation {

	private AddBeneficiaryHeaderAdapter addBeneficiaryHeaderAdapter;
	private AddBeneficiaryPayloadAdapter addBeneficiaryPayloadAdapter;

	public void setAddBeneficiaryHeaderAdapter(
			AddBeneficiaryHeaderAdapter addBeneficiaryHeaderAdapter) {
		this.addBeneficiaryHeaderAdapter = addBeneficiaryHeaderAdapter;
	}

	public void setAddBeneficiaryPayloadAdapter(
			AddBeneficiaryPayloadAdapter addBeneficiaryPayloadAdapter) {
		this.addBeneficiaryPayloadAdapter = addBeneficiaryPayloadAdapter;
	}

	public AddIndividualBeneficiaryRequest adaptRequestforAddBeneficiary(WorkContext context){

		AddIndividualBeneficiaryRequest request = new AddIndividualBeneficiaryRequest();
		request.setRequestHeader(addBeneficiaryHeaderAdapter.buildRequestHeader(context));
		request.setIndividualBeneficiaryInfo(addBeneficiaryPayloadAdapter.adaptPayload(context));
		return request;
	}

}
