package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.RetreivePayeeListDAO;
import com.barclays.bmg.service.RetreivePayeeListService;
import com.barclays.bmg.service.request.RetreivePayeeListServiceRequest;
import com.barclays.bmg.service.response.RetreivePayeeListServiceResponse;

public class RetreivePayeeListServiceImpl implements RetreivePayeeListService{

	private RetreivePayeeListDAO retreivePayeeListDAO;

	@Override
	public RetreivePayeeListServiceResponse retreivePayeeList(
			RetreivePayeeListServiceRequest retreivePayeeListServiceRequest) {

		RetreivePayeeListServiceResponse response = retreivePayeeListDAO.retreivePayeeList(retreivePayeeListServiceRequest);

		return response;
	}

	public RetreivePayeeListDAO getRetreivePayeeListDAO() {
		return retreivePayeeListDAO;
	}

	public void setRetreivePayeeListDAO(RetreivePayeeListDAO retreivePayeeListDAO) {
		this.retreivePayeeListDAO = retreivePayeeListDAO;
	}


}
