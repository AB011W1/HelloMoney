package com.barclays.bmg.service;

import com.barclays.bmg.service.request.MWalletValidateServiceRequest;
import com.barclays.bmg.service.response.MWalletValidateServiceResopnse;

public interface PayeeValidateService {

	public MWalletValidateServiceResopnse ValidateMobileNumber(MWalletValidateServiceRequest request);	
	
}
