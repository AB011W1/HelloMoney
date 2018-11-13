package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.RetreiveChargeDetailsDAO;
import com.barclays.bmg.service.RetreiveChargeDetailsService;
import com.barclays.bmg.service.request.RetreiveChargeDetailsServiceRequest;
import com.barclays.bmg.service.response.RetreiveChargeDetailsServiceResponse;

public class RetreiveChargeDetailsServiceImpl implements RetreiveChargeDetailsService{

	private RetreiveChargeDetailsDAO retreiveChargeDetailsDAO;

	@Override
	public RetreiveChargeDetailsServiceResponse retreiveChargeDetails(
			RetreiveChargeDetailsServiceRequest request) {

		return retreiveChargeDetailsDAO.retreiveChargeDetails(request);
	}

	public RetreiveChargeDetailsDAO getRetreiveChargeDetailsDAO() {
		return retreiveChargeDetailsDAO;
	}

	public void setRetreiveChargeDetailsDAO(
			RetreiveChargeDetailsDAO retreiveChargeDetailsDAO) {
		this.retreiveChargeDetailsDAO = retreiveChargeDetailsDAO;
	}

}
