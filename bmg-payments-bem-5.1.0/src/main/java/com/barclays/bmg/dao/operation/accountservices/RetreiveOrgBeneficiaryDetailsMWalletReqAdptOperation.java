package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveOrganizationBeneficiaryDetails.RetrieveOrganizationBeneficiaryDetailsRequest;
import com.barclays.bmg.dao.accountservices.adapter.RetreiveOrgBeneficiaryDtlsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.RetreiveOrgBeneficiaryDtlsMWalletPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetreiveOrgBeneficiaryDetailsMWalletReqAdptOperation {

    private RetreiveOrgBeneficiaryDtlsHeaderAdapter retreiveOrgBeneficiaryDtlsHeaderAdapter;
    private RetreiveOrgBeneficiaryDtlsMWalletPayloadAdapter beneficiaryDtlsMWalletPayloadAdapter;

    public RetrieveOrganizationBeneficiaryDetailsRequest adaptRequestforOrgBeneficiaryDetails(WorkContext context) {

	RetrieveOrganizationBeneficiaryDetailsRequest request = new RetrieveOrganizationBeneficiaryDetailsRequest();
	request.setRequestHeader(retreiveOrgBeneficiaryDtlsHeaderAdapter.buildRequestHeader(context));
	request.setOrganizationBeneficiary(beneficiaryDtlsMWalletPayloadAdapter.adaptPayload(context));

	return request;
    }

    public RetreiveOrgBeneficiaryDtlsHeaderAdapter getRetreiveOrgBeneficiaryDtlsHeaderAdapter() {
	return retreiveOrgBeneficiaryDtlsHeaderAdapter;
    }

    public void setRetreiveOrgBeneficiaryDtlsHeaderAdapter(RetreiveOrgBeneficiaryDtlsHeaderAdapter retreiveOrgBeneficiaryDtlsHeaderAdapter) {
	this.retreiveOrgBeneficiaryDtlsHeaderAdapter = retreiveOrgBeneficiaryDtlsHeaderAdapter;
    }

    /**
     * @return the beneficiaryDtlsMWalletPayloadAdapter
     */
    public RetreiveOrgBeneficiaryDtlsMWalletPayloadAdapter getBeneficiaryDtlsMWalletPayloadAdapter() {
	return beneficiaryDtlsMWalletPayloadAdapter;
    }

    /**
     * @param beneficiaryDtlsMWalletPayloadAdapter
     *            the beneficiaryDtlsMWalletPayloadAdapter to set
     */
    public void setBeneficiaryDtlsMWalletPayloadAdapter(RetreiveOrgBeneficiaryDtlsMWalletPayloadAdapter beneficiaryDtlsMWalletPayloadAdapter) {
	this.beneficiaryDtlsMWalletPayloadAdapter = beneficiaryDtlsMWalletPayloadAdapter;
    }

}
