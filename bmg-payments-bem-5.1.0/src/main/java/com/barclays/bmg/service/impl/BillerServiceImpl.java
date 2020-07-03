package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.BillerListDAO;
import com.barclays.bmg.service.BillerService;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;

public class BillerServiceImpl implements BillerService{

	private BillerListDAO billerListDAO;
	@Override
	public BillerServiceResponse getAllBillerList(BillerServiceRequest request) {

		return billerListDAO.getAllBillerList(request);
	}
	public BillerListDAO getBillerListDAO() {
		return billerListDAO;
	}
	public void setBillerListDAO(BillerListDAO billerListDAO) {
		this.billerListDAO = billerListDAO;
	}
	@Override
	public BillerServiceResponse getBillPaymentsBillerList(
			BillerServiceRequest request) {
		return billerListDAO.getBillPaymentsBillerList(request);

	}
	@Override
	public BillerServiceResponse getBillerForBillerId(
			BillerServiceRequest request) {
		// TODO Auto-generated method stub
		return billerListDAO.getBillerForBillerId(request);
	}
	@Override
	public BillerServiceResponse getActionCodeForBillerId(
			BillerServiceRequest request) {
		return billerListDAO.getActionCodeForBillerId(request);
	}
	
	//Ghana data bundle change
	@Override
	public BillerServiceResponse getDataBundleBillerList(BillerServiceRequest request) {
		// TODO Auto-generated method stub
		return billerListDAO.getDataBundleBillerList(request);
	}

}
