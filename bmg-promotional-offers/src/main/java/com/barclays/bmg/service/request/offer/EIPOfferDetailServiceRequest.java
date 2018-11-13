package com.barclays.bmg.service.request.offer;

import com.barclays.bmg.context.RequestContext;

public class EIPOfferDetailServiceRequest extends RequestContext{

	private String offerId;

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
}
