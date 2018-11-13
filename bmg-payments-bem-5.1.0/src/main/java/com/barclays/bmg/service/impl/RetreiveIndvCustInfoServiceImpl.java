package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.RetreiveIndvCustInfoByCCDAO;
import com.barclays.bmg.service.RetreiveIndvCustInfoService;
import com.barclays.bmg.service.request.RetreiveIndvCustInfoServiceRequest;
import com.barclays.bmg.service.response.RetreiveIndvCustInfoServiceResponse;

public class RetreiveIndvCustInfoServiceImpl implements RetreiveIndvCustInfoService{

	private RetreiveIndvCustInfoByCCDAO retreiveIndvCustInfoByCCDAO;
	@Override
	public RetreiveIndvCustInfoServiceResponse retreiveIndvCustByCCNumber(
			RetreiveIndvCustInfoServiceRequest request) {

		return retreiveIndvCustInfoByCCDAO.retreiveIndvCustByCCNumber(request);
	}
	public RetreiveIndvCustInfoByCCDAO getRetreiveIndvCustInfoByCCDAO() {
		return retreiveIndvCustInfoByCCDAO;
	}
	public void setRetreiveIndvCustInfoByCCDAO(
			RetreiveIndvCustInfoByCCDAO retreiveIndvCustInfoByCCDAO) {
		this.retreiveIndvCustInfoByCCDAO = retreiveIndvCustInfoByCCDAO;
	}

}
