package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.MWalletValidateServiceRequest;
import com.barclays.bmg.service.response.MWalletValidateServiceResopnse;

public interface PayeeValidateDAO {
	
	public MWalletValidateServiceResopnse validatePayee(MWalletValidateServiceRequest request);

}
