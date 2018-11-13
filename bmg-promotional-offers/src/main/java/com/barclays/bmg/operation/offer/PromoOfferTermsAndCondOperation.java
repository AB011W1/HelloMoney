package com.barclays.bmg.operation.offer;

import com.barclays.bmg.operation.request.TermsOfUseOperationRequest;
import com.barclays.bmg.operation.response.TermsOfUseOperationResponse;
import com.barclays.bmg.service.offer.PromoOfferTermsAndCondService;
import com.barclays.bmg.service.request.offer.TermsAndCondServiceRequest;
import com.barclays.bmg.service.response.offer.TermsAndCondServiceResponse;

public class PromoOfferTermsAndCondOperation {

	private PromoOfferTermsAndCondService promoOfferTermsAndCondService;

	public TermsOfUseOperationResponse retrieveTermsAndCondition(TermsOfUseOperationRequest request){

		TermsAndCondServiceRequest tcServReq = new TermsAndCondServiceRequest();
		tcServReq.setContext(request.getContext());
		tcServReq.setOfferType(request.getOfferType());

		TermsAndCondServiceResponse termsAndCondServiceResponse = promoOfferTermsAndCondService.retrieveTermsAndCondition(tcServReq);

		TermsOfUseOperationResponse termsOfUseOperationResponse = new  TermsOfUseOperationResponse();
		if(termsAndCondServiceResponse!=null && termsAndCondServiceResponse.isSuccess()){
			termsOfUseOperationResponse.setContext(termsAndCondServiceResponse.getContext());
			termsOfUseOperationResponse.setTermsAndCondition(termsAndCondServiceResponse.getTermsAndCondition());
		}

		return termsOfUseOperationResponse;

	}

	public void setPromoOfferTermsAndCondService(
			PromoOfferTermsAndCondService promoOfferTermsAndCondService) {
		this.promoOfferTermsAndCondService = promoOfferTermsAndCondService;
	}


}
