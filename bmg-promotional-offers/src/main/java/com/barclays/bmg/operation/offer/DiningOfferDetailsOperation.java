package com.barclays.bmg.operation.offer;

import com.barclays.bmg.constants.PromotionalOfferResponseCodeConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.offer.request.DiningOfferDetailsOperationRequest;
import com.barclays.bmg.operation.offer.response.DiningOfferDetailsOperationResponse;
import com.barclays.bmg.service.offer.DiningOfferService;
import com.barclays.bmg.service.request.offer.DiningOfferDetailsServiceRequest;
import com.barclays.bmg.service.response.offer.DiningOfferDetailsServiceResponse;

public class DiningOfferDetailsOperation extends BMBCommonOperation{

	private DiningOfferService diningOfferService;

	public DiningOfferDetailsOperationResponse retrieveDiningOfferDetails(DiningOfferDetailsOperationRequest request){
		Context context = request.getContext();

		DiningOfferDetailsServiceRequest diningOfferDetlsServReq = new DiningOfferDetailsServiceRequest();
		diningOfferDetlsServReq.setContext(context);
		diningOfferDetlsServReq.setOfferId(request.getOfferId());

		DiningOfferDetailsServiceResponse diningOfferDetlsServResp =
						diningOfferService.retrieveDiningOfferDetail(diningOfferDetlsServReq);

		DiningOfferDetailsOperationResponse diningOfferDetlOperResp = new DiningOfferDetailsOperationResponse();
		diningOfferDetlOperResp.setContext(diningOfferDetlsServResp.getContext());

		if(diningOfferDetlsServResp.isSuccess()){
			diningOfferDetlOperResp.setDiningOffer(diningOfferDetlsServResp.getDiningOffer());
		}
		else{
			diningOfferDetlOperResp.setSuccess(false);
			diningOfferDetlOperResp.setResCde(PromotionalOfferResponseCodeConstant.DINE_OFFER_ID_INVALID);
		}

		if (!diningOfferDetlOperResp.isSuccess()) {
			super.getMessage(diningOfferDetlOperResp);
		}

		return diningOfferDetlOperResp;
	}

	public void setDiningOfferService(DiningOfferService diningOfferService) {
		this.diningOfferService = diningOfferService;
	}


}
