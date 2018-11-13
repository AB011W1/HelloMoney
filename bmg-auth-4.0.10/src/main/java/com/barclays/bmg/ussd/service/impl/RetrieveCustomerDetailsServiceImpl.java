package com.barclays.bmg.ussd.service.impl;

import com.barclays.bmg.ussd.auth.service.request.RetrieveCustomerDetailsServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.RetrieveCustomerDetailsServiceResponse;
import com.barclays.bmg.ussd.dao.RetrieveCustomerDetailsDAO;
import com.barclays.bmg.ussd.service.RetrieveCustomerDetailsService;

public class RetrieveCustomerDetailsServiceImpl implements RetrieveCustomerDetailsService {

    private RetrieveCustomerDetailsDAO retrieveCustomerDetailsDAO;

    public RetrieveCustomerDetailsDAO getRetrieveCustomerDetailsDAO() {
	return retrieveCustomerDetailsDAO;
    }

    public void setRetrieveCustomerDetailsDAO(RetrieveCustomerDetailsDAO retrieveCustomerDetailsDAO) {
	this.retrieveCustomerDetailsDAO = retrieveCustomerDetailsDAO;
    }

    @Override
    public RetrieveCustomerDetailsServiceResponse retrieveCustomerDetails(RetrieveCustomerDetailsServiceRequest request) {

	return retrieveCustomerDetailsDAO.retrieveCustomerDetails(request);
    }

}
