package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.ViewTxnHistoryDetailsDAO;
import com.barclays.bmg.service.ViewTxnHistoryDetailsService;
import com.barclays.bmg.service.request.ViewTxnHistoryDetailsServiceRequest;
import com.barclays.bmg.service.response.ViewTxnHistoryDetailsServiceResponse;

public class ViewTxnHistoryDetailsServiceImpl implements ViewTxnHistoryDetailsService{

	private ViewTxnHistoryDetailsDAO viewTxnHistoryDetailsDAO;


	public void setViewTxnHistoryDetailsDAO(
			ViewTxnHistoryDetailsDAO viewTxnHistoryDetailsDAO) {
		this.viewTxnHistoryDetailsDAO = viewTxnHistoryDetailsDAO;
	}


	@Override
	public ViewTxnHistoryDetailsServiceResponse viewBillPaymentDetails(
			ViewTxnHistoryDetailsServiceRequest viewBillPaymentDetailsServiceRequest) {
		// TODO Auto-generated method stub
		ViewTxnHistoryDetailsServiceResponse viewBillPaymentDetailsServiceResponse=viewTxnHistoryDetailsDAO.viewBillPaymentDetails(viewBillPaymentDetailsServiceRequest);
		return viewBillPaymentDetailsServiceResponse;

	}

}
