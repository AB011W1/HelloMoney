package com.barclays.bmg.operation.offer;

import com.barclays.bmg.operation.offer.request.EIPOfferOperationRequest;
import com.barclays.bmg.operation.offer.response.EIPOfferOperationResponse;
import com.barclays.bmg.service.offer.EIPOfferService;
import com.barclays.bmg.service.request.offer.EIPServiceRequest;
import com.barclays.bmg.service.response.offer.EIPServiceResponse;

public class SearchEIPOfferOperation {

    private EIPOfferService eipOfferService;

    public EIPOfferOperationResponse searchEipOffers(EIPOfferOperationRequest request) {

	EIPServiceRequest eipServReq = new EIPServiceRequest();
	eipServReq.setContext(request.getContext());
	// eipServReq.setCategory(request.getCategory());
	// eipServReq.setOffer(request.getOffer());
	eipServReq.setPartner(request.getPartner());
	// eipServReq.setCity(request.getCity());

	EIPOfferOperationResponse eipOfferOperResp = new EIPOfferOperationResponse();

	/*
	 * EIPServiceResponse eipCategoryServResp = eipOfferService.retrieveAllCategories(eipServReq);
	 * 
	 * if(eipCategoryServResp!=null && eipCategoryServResp.isSuccess()){
	 * eipOfferOperResp.setCategoryList(eipCategoryServResp.getOfferCategoryList()); }
	 */
	EIPServiceResponse eipServResp = eipOfferService.searchEipOffers(eipServReq);

	if (eipServResp != null && eipServResp.isSuccess()) {
	    eipOfferOperResp.setContext(eipServResp.getContext());
	    eipOfferOperResp.setEipOfferList(eipServResp.getEipOfferList());
	}

	return eipOfferOperResp;
    }

    public void setEipOfferService(EIPOfferService eipOfferService) {
	this.eipOfferService = eipOfferService;
    }

}
