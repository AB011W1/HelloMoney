package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.SystemParameterDAO;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;

public class SystemParameterServiceImpl implements SystemParameterService {

    SystemParameterDAO systemParameterDAO;

    public SystemParameterListServiceResponse getSysParamByActivityId(SystemParameterServiceRequest request) {

	SystemParameterListServiceResponse systemParameterServiceResponse = systemParameterDAO.getSysParamByActivityId(request);

	return systemParameterServiceResponse;
    }

    public SystemParameterServiceResponse getSystemParameter(SystemParameterServiceRequest request) {

	SystemParameterServiceResponse systemParameterServiceResponse = systemParameterDAO.getSystemParameter(request);

	return systemParameterServiceResponse;
    }

    public SystemParameterDAO getSystemParameterDAO() {
	return systemParameterDAO;
    }

    public void setSystemParameterDAO(SystemParameterDAO systemParameterDAO) {
	this.systemParameterDAO = systemParameterDAO;
    }

	@Override
	public SystemParameterServiceResponse getStatusParameter(SystemParameterServiceRequest request) {
		// TODO Auto-generated method stub
		SystemParameterServiceResponse systemParameterServiceResponse = systemParameterDAO.getStatusParameter(request);

		return systemParameterServiceResponse;
	}

}
