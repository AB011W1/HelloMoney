package com.barclays.ussd.sysprefs.services;

import org.springframework.beans.factory.annotation.Autowired;

public class ListValueResServiceImpl implements ListValueResService {

	@Autowired
	private ListValueResDAO listValueResDAO;

	@Override
	public ListValueResServiceResponse getListValueRes(ListValueResServiceRequest request) {
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
	public ListValueResByGroupServiceResponse getListValueByGroupKits(ListValueResServiceRequest request, String bankCodeLetter) {
		return listValueResDAO.findListValueResByGroupKits(request, bankCodeLetter);
	}

	@Override
	public ListValueResByGroupServiceResponse getListValueByGroupWithFilter(ListValueResServiceRequest request) {
		return listValueResDAO.getListValueByGroupWithFilter(request);
	}

	@Override
	public ListValueResByGroupServiceResponse findListValueResByGroupKey(ListValueResServiceRequest request) {
		return listValueResDAO.findListValueResByGroupKey(request);
	}
}
