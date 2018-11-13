package com.barclays.bmg.ussd.service;

import com.barclays.bmg.service.request.RetrieveindividualCustCardListServiceRequest;
import com.barclays.bmg.service.response.RetrieveindividualCustCardListServiceResponse;



public interface RetrieveindividualCustCardListService {
	/**
     * The method is written for Retrieve individual cust cards.
     *
     * @param request
     *            the request
     * @return the RetrieveindividualCustCardListServiceResponse's object
     */
    public RetrieveindividualCustCardListServiceResponse retrieveCustCardList(RetrieveindividualCustCardListServiceRequest request);
}
