package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.InqueryBillDAO;
import com.barclays.bmg.service.InqueryBillService;
import com.barclays.bmg.service.request.InqueryBillServiceRequest;
import com.barclays.bmg.service.response.InqueryBillServiceResponse;

public class InqueryBillServiceImpl implements InqueryBillService {

	private InqueryBillDAO inqueryBillDAO;
	@Override
	public InqueryBillServiceResponse inqueryBill(
			InqueryBillServiceRequest request) {


		return inqueryBillDAO.inqueryBill(request);
	}
	public InqueryBillDAO getInqueryBillDAO() {
		return inqueryBillDAO;
	}
	public void setInqueryBillDAO(InqueryBillDAO inqueryBillDAO) {
		this.inqueryBillDAO = inqueryBillDAO;
	}

}
