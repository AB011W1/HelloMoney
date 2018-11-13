package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.EntitlementDAO;
import com.barclays.bmg.service.EntitlementService;
import com.barclays.bmg.service.product.response.EntitlementServiceResponse;
import com.barclays.bmg.service.request.EntitlementServiceRequest;

public class EntitlementServiceImpl implements EntitlementService {

    private EntitlementDAO entitlementDAO;

    @Override
    public EntitlementServiceResponse retrieveEntitlement(EntitlementServiceRequest request) {

	return entitlementDAO.retrieveEntitlement(request);
    }

    public EntitlementDAO getEntitlementDAO() {
	return entitlementDAO;
    }

    public void setEntitlementDAO(EntitlementDAO entitlementDAO) {
	this.entitlementDAO = entitlementDAO;
    }

}
