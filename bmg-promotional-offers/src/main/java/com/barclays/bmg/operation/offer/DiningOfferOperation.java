package com.barclays.bmg.operation.offer;

import java.util.List;

import com.barclays.bmg.dto.offer.DiningOfferDto;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.offer.request.DiningOfferOperationRequest;
import com.barclays.bmg.operation.offer.response.DiningOfferOperationResponse;
import com.barclays.bmg.service.offer.DiningOfferService;
import com.barclays.bmg.service.request.offer.DiningServiceRequest;
import com.barclays.bmg.service.response.offer.DiningServiceResponse;

public class DiningOfferOperation extends BMBCommonOperation {

    private DiningOfferService diningOfferService;
    private final static String OFFER_CUISINE_LIST_GROUP = "PROMO_OFFER_CUISINE_LIST";

    public DiningOfferOperationResponse retrieveAllDiningOffers(DiningOfferOperationRequest request) {
	DiningServiceRequest serviceRequest = new DiningServiceRequest();
	serviceRequest.setContext(request.getContext());

	DiningOfferOperationResponse diningOfferOprResp = new DiningOfferOperationResponse();

	/*
	 * DiningServiceResponse serviceResp = diningOfferService.retrieveAllCities(serviceRequest);
	 * 
	 * List<CityDTO> cityList = null; if(serviceResp!=null && serviceResp.isSuccess()){ cityList = serviceResp.getCityList();
	 * diningOfferOprResp.setCityList(cityList); }
	 */

	DiningServiceResponse serviceResponse = diningOfferService.retrieveAllDiningOffers(serviceRequest);

	diningOfferOprResp.setContext(serviceResponse.getContext());

	if (serviceResponse.isSuccess()) {
	    List<DiningOfferDto> dineOfferList = serviceResponse.getDiningOfferList();
	    diningOfferOprResp.setDiningOfferList(dineOfferList);
	}

	// diningOfferOprResp.setCuisineList(getListValueByGroup(request.getContext(), OFFER_CUISINE_LIST_GROUP));

	return diningOfferOprResp;
    }

    public void setDiningOfferService(DiningOfferService diningOfferService) {
	this.diningOfferService = diningOfferService;
    }

}
