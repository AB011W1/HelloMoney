package com.barclays.bmg.service.product.impl;

import com.barclays.bmg.dao.product.ComponentResDAO;
import com.barclays.bmg.service.product.ComponentResService;
import com.barclays.bmg.service.product.request.ComponentResServiceRequest;
import com.barclays.bmg.service.product.response.ComponentResServiceResponse;

public class ComponentResServiceImpl implements ComponentResService {

    private ComponentResDAO componentResDAO;

    @Override
    public ComponentResServiceResponse getComponentResCache(ComponentResServiceRequest request) {

	return componentResDAO.getComponentResCache(request);
    }

    public ComponentResDAO getComponentResDAO() {
	return componentResDAO;
    }

    public void setComponentResDAO(ComponentResDAO componentResDAO) {
	this.componentResDAO = componentResDAO;
    }

}
