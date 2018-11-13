package com.barclays.bmg.operation.offer;

import com.barclays.bmg.constants.PromotionalOfferResponseCodeConstant;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.offer.request.EIPOfferDetailOperationRequest;
import com.barclays.bmg.operation.offer.response.EIPOfferDetailOperationResponse;
import com.barclays.bmg.service.offer.EIPOfferService;
import com.barclays.bmg.service.request.offer.EIPOfferDetailServiceRequest;
import com.barclays.bmg.service.response.offer.EIPOfferDetailServiceResponse;

public class EIPOfferDetailOperation extends BMBCommonOperation{

	private EIPOfferService eipOfferService;


	public EIPOfferDetailOperationResponse retrieveAllEipOffersDetail (
			EIPOfferDetailOperationRequest request) {

		EIPOfferDetailServiceRequest eipServDetlReq = new EIPOfferDetailServiceRequest();
		eipServDetlReq.setContext(request.getContext());
		eipServDetlReq.setOfferId(request.getOfferId());

		EIPOfferDetailServiceResponse eipServDetlResp = eipOfferService.retrieveEipOfferDetails(eipServDetlReq);

		EIPOfferDetailOperationResponse eipOfferDetlOperResp = new EIPOfferDetailOperationResponse();
		eipOfferDetlOperResp.setContext(eipServDetlResp.getContext());

		if(eipServDetlResp.isSuccess()){
			eipOfferDetlOperResp.setEipOffer(eipServDetlResp.getEipOffer());
		}
		else{
			eipOfferDetlOperResp.setSuccess(false);
			eipOfferDetlOperResp.setResCde(PromotionalOfferResponseCodeConstant.EIP_OFFER_ID_INVALID);
		}

		if (!eipOfferDetlOperResp.isSuccess()) {
			super.getMessage(eipOfferDetlOperResp);
		}

		return eipOfferDetlOperResp;
	}


	public void setEipOfferService(EIPOfferService eipOfferService) {
		this.eipOfferService = eipOfferService;
	}


}
