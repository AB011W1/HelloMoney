package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.AddOrganizationBeneficiary.AddOrganizationBeneficiaryRequest;
import com.barclays.bmg.dao.accountservices.adapter.AddOrgBeneficiaryDtlsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.AddOrgBeneficiaryDtlsPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;
//
/**
 * @author E20041929
 *
 */
public class AddOrgBeneficiaryReqAdptOperation {

 	private AddOrgBeneficiaryDtlsHeaderAdapter addOrgBeneficiaryDtlsHeaderAdapter;
	private AddOrgBeneficiaryDtlsPayloadAdapter addOrgBeneficiaryDtlsPayloadAdapter;

 	public AddOrganizationBeneficiaryRequest adaptRequestforAddOrgBeneficiary(WorkContext context){
 		AddOrganizationBeneficiaryRequest request = new AddOrganizationBeneficiaryRequest();
		request.setRequestHeader(addOrgBeneficiaryDtlsHeaderAdapter.buildRequestHeader(context));
        request.setAddOrganizationBeneficiary(addOrgBeneficiaryDtlsPayloadAdapter.adaptPayload(context));
		return request;
	}
	public AddOrgBeneficiaryDtlsHeaderAdapter getaddOrgBeneficiaryDtlsHeaderAdapter() {
		return addOrgBeneficiaryDtlsHeaderAdapter;
	}
	public void setaddOrgBeneficiaryDtlsHeaderAdapter(
			AddOrgBeneficiaryDtlsHeaderAdapter addOrgBeneficiaryDtlsHeaderAdapter) {
		this.addOrgBeneficiaryDtlsHeaderAdapter = addOrgBeneficiaryDtlsHeaderAdapter;
	}
	public AddOrgBeneficiaryDtlsPayloadAdapter getaddOrgBeneficiaryDtlsPayloadAdapter() {
		return addOrgBeneficiaryDtlsPayloadAdapter;
	}
	public void setaddOrgBeneficiaryDtlsPayloadAdapter(
			AddOrgBeneficiaryDtlsPayloadAdapter addOrgBeneficiaryDtlsPayloadAdapter) {
		this.addOrgBeneficiaryDtlsPayloadAdapter = addOrgBeneficiaryDtlsPayloadAdapter;
	}
}
