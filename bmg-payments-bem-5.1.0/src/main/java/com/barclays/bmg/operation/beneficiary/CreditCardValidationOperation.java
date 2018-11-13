package com.barclays.bmg.operation.beneficiary;

import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.CreditCardValidationOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.CreditCardValidationOperationResponse;
import com.barclays.bmg.service.RetreiveIndvCustInfoService;
import com.barclays.bmg.service.request.RetreiveIndvCustInfoServiceRequest;
import com.barclays.bmg.service.response.RetreiveIndvCustInfoServiceResponse;

public class CreditCardValidationOperation extends BMBPaymentsOperation {

    RetreiveIndvCustInfoService retreiveIndvCustInfoService;

    public CreditCardValidationOperationResponse validateCreditCard(CreditCardValidationOperationRequest request) {
	Context context = request.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
	CreditCardValidationOperationResponse response = new CreditCardValidationOperationResponse();
	response.setContext(context);

	String crdNo = request.getCrdNo();

	RetreiveIndvCustInfoServiceRequest retreiveIndvCustInfoServiceRequest = new RetreiveIndvCustInfoServiceRequest();
	retreiveIndvCustInfoServiceRequest.setContext(context);
	retreiveIndvCustInfoServiceRequest.setCreditCardNo(crdNo);

	RetreiveIndvCustInfoServiceResponse retreiveIndvCustInfoServiceResponse = retreiveIndvCustInfoService
		.retreiveIndvCustByCCNumber(retreiveIndvCustInfoServiceRequest);
	CustomerDTO custDTO = null;

	if (retreiveIndvCustInfoServiceResponse.isSuccess()) {

	    custDTO = retreiveIndvCustInfoServiceResponse.getCustDTO();

	    if (custDTO != null) {
		response.setSuccess(true);
		CreditCardAccountDTO creditCardAccountDTO = custDTO.getCreditCardAccountDTO();
		response.setCreditCardAccountDTO(creditCardAccountDTO);
		response.setResCde(AccountErrorCodeConstant.SUCCESS_CODE);

	    } else {

		response.setSuccess(false);
		response.setResCde(BillPaymentResponseCodeConstants.BCD_INVALID_CARD_NUMBER);
	    }
	} else {
	    response.setSuccess(false);
	    response.setResCde(BillPaymentResponseCodeConstants.BP_BCD_PRIME_06878);
	}

	if (!response.isSuccess()) {
	    getMessage(response);
	}
	return response;

    }

    public void setRetreiveIndvCustInfoService(RetreiveIndvCustInfoService retreiveIndvCustInfoService) {
	this.retreiveIndvCustInfoService = retreiveIndvCustInfoService;
    }

}
