package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.Beneficiary.Beneficiary;
import com.barclays.bem.DeleteIndividualCustBeneficiary.IndividualBeneficiary;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.service.request.DeleteBeneficiaryServiceRequest;

/**
 * @author BTCI
 *
 */
public class DeleteBeneficiaryPayloadAdapter {

	/**
	 * @param workContext
	 * @return IndividualBeneficiary
	 */
	public IndividualBeneficiary adaptPayload(WorkContext workContext) {

		IndividualBeneficiary requestBody = new IndividualBeneficiary();

		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		DeleteBeneficiaryServiceRequest deleteBeneficiaryServiceRequest = (DeleteBeneficiaryServiceRequest) args[0];
		Context context = deleteBeneficiaryServiceRequest.getContext();
		requestBody.setCustomerNumber(context.getCustomerId());

		Beneficiary individualBeneficiary = new Beneficiary();
		individualBeneficiary.setBeneficiaryID(deleteBeneficiaryServiceRequest
				.getPayeeId());
		BeneficiaryDTO beneficiaryDTO = deleteBeneficiaryServiceRequest
				.getBeneficiaryDTO();

		requestBody.setAutoAuthorize("true");
		requestBody.setCustomerAuthMechanismTypeCode(beneficiaryDTO
				.getCustomerAuthMechanismTypeCode());
		requestBody.setIndividualBeneficiary(individualBeneficiary);
		requestBody.setCustomerNumber(deleteBeneficiaryServiceRequest
				.getContext().getCustomerId());
		return requestBody;
	}

}
