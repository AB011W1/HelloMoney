package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.Beneficiary.Beneficiary;
import com.barclays.bem.RetrieveOrganizationBeneficiaryDetails.OrganizationBeneficiary;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.CopyOfRetreiveBeneficiaryDetailsServiceRequest;

public class RetreiveOrgBeneficiaryDtlsMWalletPayloadAdapter {

	public OrganizationBeneficiary adaptPayload(WorkContext workContext){

		OrganizationBeneficiary requestBody = new OrganizationBeneficiary();

		DAOContext daoContext = (DAOContext)workContext;

		Object[] args = daoContext.getArguments();

		CopyOfRetreiveBeneficiaryDetailsServiceRequest retreiveBeneficiaryDetailsServiceRequest=
									(CopyOfRetreiveBeneficiaryDetailsServiceRequest)args[0];
		Context context = retreiveBeneficiaryDetailsServiceRequest.getContext();
		requestBody.setCustomerNumber(context.getCustomerId());

		Beneficiary beneficiary = new Beneficiary();
		requestBody.setBeneficiary(beneficiary);
		return requestBody;
	}
}
