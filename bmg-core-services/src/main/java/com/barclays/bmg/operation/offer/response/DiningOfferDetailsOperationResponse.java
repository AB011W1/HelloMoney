package com.barclays.bmg.operation.offer.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.offer.DiningOfferDto;

public class DiningOfferDetailsOperationResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = -2982278196864093505L;
    private DiningOfferDto diningOffer;

    public DiningOfferDto getDiningOffer() {
	return diningOffer;
    }

    public void setDiningOffer(DiningOfferDto diningOffer) {
	this.diningOffer = diningOffer;
    }
}