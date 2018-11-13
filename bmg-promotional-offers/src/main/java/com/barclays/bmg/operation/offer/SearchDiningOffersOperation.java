package com.barclays.bmg.operation.offer;

import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.offer.request.DiningOfferOperationRequest;
import com.barclays.bmg.operation.offer.response.DiningOfferOperationResponse;
import com.barclays.bmg.service.offer.DiningOfferService;
import com.barclays.bmg.service.request.offer.DiningServiceRequest;
import com.barclays.bmg.service.response.offer.DiningServiceResponse;

public class SearchDiningOffersOperation extends BMBCommonOperation {

    private DiningOfferService diningOfferService;

    public DiningOfferOperationResponse searchDiningOffers(DiningOfferOperationRequest request) {

	DiningServiceRequest diningServReq = new DiningServiceRequest();
	diningServReq.setContext(request.getContext());
	diningServReq.setResturantName(request.getResturantName());
	diningServReq.setCity(request.getCity());

	DiningOfferOperationResponse diningOfferOprResp = new DiningOfferOperationResponse();

	DiningServiceResponse diningServResp = diningOfferService.searchDiningOffers(diningServReq);

	if (diningServResp != null && diningServResp.isSuccess()) {
	    diningOfferOprResp.setContext(diningServResp.getContext());

	    diningOfferOprResp.setDiningOfferList(diningServResp.getDiningOfferList());

	}

	return diningOfferOprResp;

    }

    public void setDiningOfferService(DiningOfferService diningOfferService) {
	this.diningOfferService = diningOfferService;
    }

}
