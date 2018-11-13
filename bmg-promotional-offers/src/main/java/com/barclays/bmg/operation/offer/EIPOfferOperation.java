package com.barclays.bmg.operation.offer;

import com.barclays.bmg.operation.offer.request.EIPOfferOperationRequest;
import com.barclays.bmg.operation.offer.response.EIPOfferOperationResponse;
import com.barclays.bmg.service.offer.EIPOfferService;
import com.barclays.bmg.service.request.offer.EIPServiceRequest;
import com.barclays.bmg.service.response.offer.EIPServiceResponse;

public class EIPOfferOperation {

	private EIPOfferService eipOfferService;


	public EIPOfferOperationResponse retrieveAllEipOffers(
			EIPOfferOperationRequest request) {

		EIPServiceRequest eipServiceRequest = new EIPServiceRequest();
		eipServiceRequest.setContext(request.getContext());

		EIPOfferOperationResponse eipOfferOperationResponse = new EIPOfferOperationResponse();

		EIPServiceResponse eipCategoryServResp = eipOfferService.retrieveAllCategories(eipServiceRequest);

		if(eipCategoryServResp!=null && eipCategoryServResp.isSuccess()){
			eipOfferOperationResponse.setCategoryList(eipCategoryServResp.getOfferCategoryList());
		}

		EIPServiceResponse eipServiceResponse = eipOfferService.retrieveAllEipOffers(eipServiceRequest);

		if(eipServiceResponse!=null && eipServiceResponse.isSuccess()){
			eipOfferOperationResponse.setEipOfferList(eipServiceResponse.getEipOfferList());
			eipOfferOperationResponse.setContext(eipServiceResponse.getContext());
		}

		return eipOfferOperationResponse;
	}

	public void setEipOfferService(EIPOfferService eipOfferService) {
		this.eipOfferService = eipOfferService;
	}

}

