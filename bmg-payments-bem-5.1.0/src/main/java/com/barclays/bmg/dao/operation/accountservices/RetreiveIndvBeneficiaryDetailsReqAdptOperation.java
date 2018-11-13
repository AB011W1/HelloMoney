package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveIndividualBeneficiaryDetails.RetrieveIndividualBeneficiaryDetailsRequest;
import com.barclays.bmg.dao.accountservices.adapter.RetreiveIndvBeneficiaryDtlsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.RetreiveIndvBeneficiaryDtlsPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetreiveIndvBeneficiaryDetailsReqAdptOperation {

	private RetreiveIndvBeneficiaryDtlsHeaderAdapter retreiveIndvBeneficiaryDtlsHeaderAdapter;
	private RetreiveIndvBeneficiaryDtlsPayloadAdapter retreiveIndvBeneficiaryDtlsPayloadAdapter;
	public RetrieveIndividualBeneficiaryDetailsRequest adaptRequestforOrgBeneficiaryDetails(WorkContext context){

		RetrieveIndividualBeneficiaryDetailsRequest request = new RetrieveIndividualBeneficiaryDetailsRequest();
		request.setRequestHeader(retreiveIndvBeneficiaryDtlsHeaderAdapter.buildRequestHeader(context));
		request.setIndividualBeneficiary(retreiveIndvBeneficiaryDtlsPayloadAdapter.adaptPayload(context));

		return request;
	}
	public RetreiveIndvBeneficiaryDtlsHeaderAdapter getRetreiveIndvBeneficiaryDtlsHeaderAdapter() {
		return retreiveIndvBeneficiaryDtlsHeaderAdapter;
	}

	public void setRetreiveIndvBeneficiaryDtlsHeaderAdapter(
			RetreiveIndvBeneficiaryDtlsHeaderAdapter retreiveIndvBeneficiaryDtlsHeaderAdapter) {
		this.retreiveIndvBeneficiaryDtlsHeaderAdapter = retreiveIndvBeneficiaryDtlsHeaderAdapter;
	}
	public RetreiveIndvBeneficiaryDtlsPayloadAdapter getRetreiveIndvBeneficiaryDtlsPayloadAdapter() {
		return retreiveIndvBeneficiaryDtlsPayloadAdapter;
	}
	public void setRetreiveIndvBeneficiaryDtlsPayloadAdapter(
			RetreiveIndvBeneficiaryDtlsPayloadAdapter retreiveIndvBeneficiaryDtlsPayloadAdapter) {
		this.retreiveIndvBeneficiaryDtlsPayloadAdapter = retreiveIndvBeneficiaryDtlsPayloadAdapter;
	}
}
