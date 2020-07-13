package com.barclays.bmg.operation;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.service.PayeeValidateService;
import com.barclays.bmg.service.request.MWalletValidateServiceRequest;
import com.barclays.bmg.service.response.MWalletValidateServiceResopnse;

public class MWalletPayeeValidateOperation extends BMBPaymentsOperation{

	private PayeeValidateService validateService;
	
	
	public PayeeValidateService getValidateService() {
		return validateService;
	}


	public void setValidateService(PayeeValidateService validateService) {
		this.validateService = validateService;
	}


	public MWalletValidateServiceResopnse validateMobileNumber(MWalletValidateServiceRequest request ) {
		
		loadParameters(request.getContext(), ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
		return validateService.ValidateMobileNumber(request);
	}
	
}
