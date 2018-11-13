package com.barclays.bmg.service.accountdetails.impl;

import com.barclays.bmg.dao.accountdetails.CASADetailsDAO;
import com.barclays.bmg.service.accountdetails.CASADetailsService;
import com.barclays.bmg.service.accountdetails.request.CASAAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.request.CASADetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CASAAccountActivityServiceResponse;
import com.barclays.bmg.service.accountdetails.response.CASAAccountTrnxHistoryServiceResponse;
import com.barclays.bmg.service.accountdetails.response.CASADetailsServiceResponse;

public class CASADetailsServiceImpl implements CASADetailsService {

    /*
     * Added for the Mini Statment (non-Javadoc)
     * 
     * @seecom.barclays.bmg.service.accountdetails.CASADetailsService#retrieveAcctTransactionHistory(com.barclays.bmg.service.accountdetails.request.
     * CASAAccountActivityServiceRequest)
     */
    @Override
    public CASAAccountTrnxHistoryServiceResponse retrieveAcctTransactionHistory(CASAAccountActivityServiceRequest request) {
	return casaDetailsDAO.retrieveAcctTransactionHistory(request);
    }

    CASADetailsDAO casaDetailsDAO;

    @Override
    public CASADetailsServiceResponse retrieveCASADetails(CASADetailsServiceRequest request) {
	// TODO Auto-generated method stub

	return casaDetailsDAO.retrieveCASADetails(request);

    }

    @Override
    public CASAAccountActivityServiceResponse retrieveCASAAccountActivity(CASAAccountActivityServiceRequest request) {
	// TODO Auto-generated method stub
	return casaDetailsDAO.retrieveCASATransactionActivity(request);
    }

    public void setCasaDetailsDAO(CASADetailsDAO casaDetailsDAO) {
	this.casaDetailsDAO = casaDetailsDAO;
    }

}
