package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.AddIndividualBeneficiary.AddIndividualBeneficiaryResponse;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.service.response.AddBeneficiaryServiceResponse;

/**
 * @author BTCI
 *
 */
public class AddBeneficiaryResAdptOperation extends AbstractResAdptOperation{


	/**
	 * @param workContext
	 * @param obj
	 * @return AddBeneficiaryServiceResponse
	 * Response adapter for BEM service response
	 *
	 */
	public AddBeneficiaryServiceResponse adaptResponse(WorkContext workContext, Object obj){

		AddBeneficiaryServiceResponse response =
			new AddBeneficiaryServiceResponse();

		AddIndividualBeneficiaryResponse bemResponse = (AddIndividualBeneficiaryResponse) obj;
		if(bemResponse!=null){
		if(null!=bemResponse.getTransactionResponseStatus())
		response.setTxnReferenceNumber(bemResponse.getTransactionResponseStatus().getTransactionReferenceNumber());

		response.setResCde(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
		response.setResMsg(bemResponse.getResponseHeader().getServiceResStatus().getServiceResDesc());

		if(checkResponseHeader(bemResponse.getResponseHeader())){
			response.setSuccess(true);
			return response;
		}
		}
		response.setSuccess(false);
		return response;
	}

}
