package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.DeleteIndividualCustBeneficiary.DeleteIndividualCustomerBeneficiaryResponse;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.service.response.DeleteBeneficiaryServiceResponse;

/**
 * @author BTCI
 *
 */
public class DeleteBeneficiaryResAdptOperation extends AbstractResAdptOperationAcct {

	/**
	 * @param workContext
	 * @param obj
	 * @return DeleteBeneficiaryServiceResponse
	 */
	public DeleteBeneficiaryServiceResponse adaptResponse(
			WorkContext workContext, Object obj) {

		DeleteBeneficiaryServiceResponse response = new DeleteBeneficiaryServiceResponse();

		DeleteIndividualCustomerBeneficiaryResponse bemResponse = (DeleteIndividualCustomerBeneficiaryResponse) obj;

		if (null != bemResponse.getTransactionResponseStatus())
			response.setTxnReferenceNumber(bemResponse
					.getTransactionResponseStatus()
					.getTransactionReferenceNumber());
		if (checkResponseHeader(bemResponse.getResponseHeader())) {
			response.setSuccess(true);
		} else {
			response.setSuccess(false);
		}
		return response;
	}

}
