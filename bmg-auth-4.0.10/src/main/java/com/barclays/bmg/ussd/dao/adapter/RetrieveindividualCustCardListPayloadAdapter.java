package com.barclays.bmg.ussd.dao.adapter;

import com.barclays.bem.RetrieveIndividualCustCardList.CustomerCardList;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.RetrieveindividualCustCardListServiceRequest;

public class RetrieveindividualCustCardListPayloadAdapter {

	//TODO chnage the request body from bem stub.jar
	public CustomerCardList adaptPayLoad(WorkContext workContext) {

		CustomerCardList requestBody = new CustomerCardList();

		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		RetrieveindividualCustCardListServiceRequest retrieveindividualCustCardListServiceRequest = (RetrieveindividualCustCardListServiceRequest) args[0];
		Context context = retrieveindividualCustCardListServiceRequest.getContext();
		//requestBody.setCustomerNumber(context.getCustomerId());
		requestBody.setAccountNumber(retrieveindividualCustCardListServiceRequest.getAccountNo());
		return requestBody;
	    }
}
