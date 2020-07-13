package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.PayeeValidateDAO;
import com.barclays.bmg.service.PayeeValidateService;
import com.barclays.bmg.service.request.MWalletValidateServiceRequest;
import com.barclays.bmg.service.response.MWalletValidateServiceResopnse;

public class PayeeValidateServiceImpl implements PayeeValidateService {
	
	
	private PayeeValidateDAO payeeValidateDAO;

	public PayeeValidateDAO getPayeeValidateDAO() {
		return payeeValidateDAO;
	}

	public void setPayeeValidateDAO(PayeeValidateDAO payeeValidateDAO) {
		this.payeeValidateDAO = payeeValidateDAO;
	}

	@Override
	public MWalletValidateServiceResopnse ValidateMobileNumber(MWalletValidateServiceRequest request) {
		// TODO Auto-generated method stub
		return payeeValidateDAO.validatePayee(request);
	}

}
