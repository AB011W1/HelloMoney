package com.barclays.bmg.service.request.offer;

import com.barclays.bmg.context.RequestContext;

public class TermsAndCondServiceRequest extends RequestContext{

	private String offerType;

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

}
