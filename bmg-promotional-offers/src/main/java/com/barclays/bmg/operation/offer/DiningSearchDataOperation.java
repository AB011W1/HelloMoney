package com.barclays.bmg.operation.offer;

import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.offer.request.DiningSearchDataOperationRequest;
import com.barclays.bmg.operation.offer.response.DiningSearchDataOperationResponse;

public class DiningSearchDataOperation extends BMBCommonOperation{

	private final static String OFFER_CUISINE_LIST_GROUP = "PROMO_OFFER_CUISINE_LIST";

	public DiningSearchDataOperationResponse retrieveSearchData(DiningSearchDataOperationRequest request){

		DiningSearchDataOperationResponse response = new DiningSearchDataOperationResponse();

		response.setListValueDtoList(getListValueByGroup(request.getContext(), OFFER_CUISINE_LIST_GROUP));

		return response;

	}

}
