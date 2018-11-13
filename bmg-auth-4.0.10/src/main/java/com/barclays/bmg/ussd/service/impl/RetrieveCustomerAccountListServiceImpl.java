package com.barclays.bmg.ussd.service.impl;

import com.barclays.bmg.ussd.auth.service.request.RetrieveCustomerAccountListServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.RetrieveCustomerAccountListServiceResponse;
import com.barclays.bmg.ussd.dao.RetrieveCustomerAccountListDAO;
import com.barclays.bmg.ussd.service.RetrieveCustomerAccountListService;

public class RetrieveCustomerAccountListServiceImpl implements RetrieveCustomerAccountListService {

    private RetrieveCustomerAccountListDAO retrieveCustomerAccountListDAO;

    public RetrieveCustomerAccountListDAO getRetrieveCustomerAccountListDAO() {
	return retrieveCustomerAccountListDAO;
    }

    public void setRetrieveCustomerAccountListDAO(RetrieveCustomerAccountListDAO retrieveCustomerAccountListDAO) {
	this.retrieveCustomerAccountListDAO = retrieveCustomerAccountListDAO;
    }

    @Override
    public RetrieveCustomerAccountListServiceResponse retrieveCustomerAccount(RetrieveCustomerAccountListServiceRequest request) {

	return retrieveCustomerAccountListDAO.retrieveCustomerAccount(request);
    }

}
