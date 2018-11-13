package com.barclays.bmg.service.accountdetails.impl;

import com.barclays.bmg.dao.accountdetails.CASAAccountActivityDAO;
import com.barclays.bmg.service.accountdetails.CASAAccountActivityService;
import com.barclays.bmg.service.accountdetails.request.CASAAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CASAAccountActivityServiceResponse;

public class CASAAccountActivityServiceImpl implements CASAAccountActivityService {

    private CASAAccountActivityDAO casaAccountActivityDAO;

    @Override
    public CASAAccountActivityServiceResponse retrieveCASAAccountActivity(CASAAccountActivityServiceRequest request) {
	// TODO Auto-generated method stub

	return casaAccountActivityDAO.retrieveCASADetails(request);

    }

    public CASAAccountActivityDAO getCasaAccountActivityDAO() {
	return casaAccountActivityDAO;
    }

    public void setCasaAccountActivityDAO(CASAAccountActivityDAO casaAccountActivityDAO) {
	this.casaAccountActivityDAO = casaAccountActivityDAO;
    }

}
