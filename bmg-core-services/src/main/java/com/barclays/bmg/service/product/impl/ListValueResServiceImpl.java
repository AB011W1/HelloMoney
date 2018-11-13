package com.barclays.bmg.service.product.impl;

import com.barclays.bmg.dao.product.ListValueResDAO;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.product.response.ListValueResServiceResponse;

public class ListValueResServiceImpl implements ListValueResService {

    private ListValueResDAO listValueResDAO;

    @Override
    public ListValueResServiceResponse getListValueRes(ListValueResServiceRequest request) {
	// TODO Auto-generated method stub

	return listValueResDAO.findListValueLabel(request);
    }

    public ListValueResDAO getListValueResDAO() {
	return listValueResDAO;
    }

    public void setListValueResDAO(ListValueResDAO listValueResDAO) {
	this.listValueResDAO = listValueResDAO;
    }

    @Override
    public ListValueResByGroupServiceResponse getListValueByGroup(ListValueResServiceRequest request) {

	return listValueResDAO.findListValueResByGroup(request);
    }

    @Override
    public ListValueResByGroupServiceResponse getListValueByGroupWithFilter(ListValueResServiceRequest request) {

	return listValueResDAO.getListValueByGroupWithFilter(request);
    }

    @Override
    public ListValueResByGroupServiceResponse getListValueByKey(ListValueResServiceRequest request) {
	// TODO Auto-generated method stub
	return listValueResDAO.findListValueResByKey(request);
    }

}
