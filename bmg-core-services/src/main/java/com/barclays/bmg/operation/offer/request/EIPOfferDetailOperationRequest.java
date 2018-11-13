package com.barclays.bmg.operation.offer.request;

import com.barclays.bmg.context.RequestContext;

public class EIPOfferDetailOperationRequest extends RequestContext {

    private String offerId;

    public String getOfferId() {
	return offerId;
    }

    public void setOfferId(String offerId) {
	this.offerId = offerId;
    }

}
