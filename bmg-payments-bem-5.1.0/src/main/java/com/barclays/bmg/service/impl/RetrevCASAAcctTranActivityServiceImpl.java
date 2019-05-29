package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.RetrevCASAAcctTranActivityDAO;
import com.barclays.bmg.service.accounts.RetrevCASAAcctTranActivityService;
import com.barclays.bmg.service.accounts.request.RetrevCASAAcctTranActivityServiceRequest;
import com.barclays.bmg.service.accounts.response.RetrevCASAAcctTranActivityServiceResponse;

public class RetrevCASAAcctTranActivityServiceImpl implements
		RetrevCASAAcctTranActivityService {
	RetrevCASAAcctTranActivityDAO retrevCASAAcctTranActivityDAO;

	public RetrevCASAAcctTranActivityDAO getRetrevCASAAcctTranActivityDAO() {
		return retrevCASAAcctTranActivityDAO;
	}

	public void setRetrevCASAAcctTranActivityDAO(
			RetrevCASAAcctTranActivityDAO retrevCASAAcctTranActivityDAO) {
		this.retrevCASAAcctTranActivityDAO = retrevCASAAcctTranActivityDAO;
	}

	@Override
	public RetrevCASAAcctTranActivityServiceResponse retrevCasaAcctTranActivity(
			RetrevCASAAcctTranActivityServiceRequest request) {
		// TODO Auto-generated method stub
		RetrevCASAAcctTranActivityServiceResponse retrevCASAAcctTranActivityServiceResponse = retrevCASAAcctTranActivityDAO
				.retrevCasaAcctTranActivity(request);

		return retrevCASAAcctTranActivityServiceResponse;
	}

}
