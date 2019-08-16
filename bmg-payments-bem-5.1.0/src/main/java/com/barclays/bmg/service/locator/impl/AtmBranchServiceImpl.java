package com.barclays.bmg.service.locator.impl;

import com.barclays.bmg.dao.lookup.AtmBranchDAO;
import com.barclays.bmg.service.AtmBranchService;
import com.barclays.bmg.service.request.AtmBranchServiceRequest;
import com.barclays.bmg.service.response.AtmBranchServiceResponse;

public class AtmBranchServiceImpl implements AtmBranchService {

    private AtmBranchDAO AtmBranchDAO;

    @Override
    public AtmBranchServiceResponse retrieveAtmBranchList(AtmBranchServiceRequest request) {
	AtmBranchServiceResponse response = null;
	if (request.getActivityId().equalsIgnoreCase("ATMLocator")) {
	    response = AtmBranchDAO.retrieveATMList(request);
	}
	if (request.getActivityId().equalsIgnoreCase("BranchLocator")) {
	    response = AtmBranchDAO.retrieveBranchList(request);
	}

	return response;
    }

    public void setAtmBranchDAO(AtmBranchDAO atmBranchDAO) {
	AtmBranchDAO = atmBranchDAO;
    }

    @Override
    public AtmBranchServiceResponse retrieveATMList(AtmBranchServiceRequest request) {
	AtmBranchServiceResponse response = null;
	response = AtmBranchDAO.retrieveATMList(request);
	return response;
    }

    @Override
    public AtmBranchServiceResponse retrieveBranchList(AtmBranchServiceRequest request) {
	AtmBranchServiceResponse response = null;
	response = AtmBranchDAO.retrieveBranchList(request);
	return response;
    }

}