package com.barclays.bmg.operation.request;

import com.barclays.bmg.context.RequestContext;

public class TermsOfUseOperationRequest extends RequestContext {

    private String offerType;

    public String getOfferType() {
	return offerType;
    }

    public void setOfferType(String offerType) {
	this.offerType = offerType;
    }

}
