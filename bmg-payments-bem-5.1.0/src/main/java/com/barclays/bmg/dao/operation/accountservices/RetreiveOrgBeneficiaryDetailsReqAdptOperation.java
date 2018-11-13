package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveOrganizationBeneficiaryDetails.RetrieveOrganizationBeneficiaryDetailsRequest;
import com.barclays.bmg.dao.accountservices.adapter.RetreiveOrgBeneficiaryDtlsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.RetreiveOrgBeneficiaryDtlsPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetreiveOrgBeneficiaryDetailsReqAdptOperation {

	private RetreiveOrgBeneficiaryDtlsHeaderAdapter retreiveOrgBeneficiaryDtlsHeaderAdapter;
	private RetreiveOrgBeneficiaryDtlsPayloadAdapter retreiveOrgBeneficiaryDtlsPayloadAdapter;
	public RetrieveOrganizationBeneficiaryDetailsRequest adaptRequestforOrgBeneficiaryDetails(WorkContext context){

		RetrieveOrganizationBeneficiaryDetailsRequest request = new RetrieveOrganizationBeneficiaryDetailsRequest();
		request.setRequestHeader(retreiveOrgBeneficiaryDtlsHeaderAdapter.buildRequestHeader(context));
		request.setOrganizationBeneficiary(retreiveOrgBeneficiaryDtlsPayloadAdapter.adaptPayload(context));

		return request;
	}
	public RetreiveOrgBeneficiaryDtlsHeaderAdapter getRetreiveOrgBeneficiaryDtlsHeaderAdapter() {
		return retreiveOrgBeneficiaryDtlsHeaderAdapter;
	}
	public void setRetreiveOrgBeneficiaryDtlsHeaderAdapter(
			RetreiveOrgBeneficiaryDtlsHeaderAdapter retreiveOrgBeneficiaryDtlsHeaderAdapter) {
		this.retreiveOrgBeneficiaryDtlsHeaderAdapter = retreiveOrgBeneficiaryDtlsHeaderAdapter;
	}
	public RetreiveOrgBeneficiaryDtlsPayloadAdapter getRetreiveOrgBeneficiaryDtlsPayloadAdapter() {
		return retreiveOrgBeneficiaryDtlsPayloadAdapter;
	}
	public void setRetreiveOrgBeneficiaryDtlsPayloadAdapter(
			RetreiveOrgBeneficiaryDtlsPayloadAdapter retreiveOrgBeneficiaryDtlsPayloadAdapter) {
		this.retreiveOrgBeneficiaryDtlsPayloadAdapter = retreiveOrgBeneficiaryDtlsPayloadAdapter;
	}
}
