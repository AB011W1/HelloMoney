package com.barclays.bmg.ussd.service.impl;


import com.barclays.bmg.service.request.RetrieveindividualCustCardListServiceRequest;
import com.barclays.bmg.service.response.RetrieveindividualCustCardListServiceResponse;
import com.barclays.bmg.ussd.dao.RetrieveindividualCustCardListDAO;
import com.barclays.bmg.ussd.service.RetrieveindividualCustCardListService;

public class RetrieveindividualCustCardListServiceImpl implements
	RetrieveindividualCustCardListService {

	private RetrieveindividualCustCardListDAO retrieveindividualCustCardListDAO;
	@Override
	public RetrieveindividualCustCardListServiceResponse retrieveCustCardList(
			RetrieveindividualCustCardListServiceRequest request) {
		// TODO Auto-generated method stub
		return	retrieveindividualCustCardListDAO.retrieveCustCardList(request);

	}


	    public RetrieveindividualCustCardListDAO getRetrieveindividualCustCardListDAO() {
		return retrieveindividualCustCardListDAO;
	}

	public void setRetrieveindividualCustCardListDAO(
			RetrieveindividualCustCardListDAO retrieveindividualCustCardListDAO) {
		this.retrieveindividualCustCardListDAO = retrieveindividualCustCardListDAO;
	}




}
