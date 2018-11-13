package com.barclays.bmg.service.response.offer;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.offer.DiningOfferDto;

public class DiningOfferDetailsServiceResponse extends ResponseContext {
	private DiningOfferDto diningOffer;

	public DiningOfferDto getDiningOffer() {
		return diningOffer;
	}

	public void setDiningOffer(DiningOfferDto diningOffer) {
		this.diningOffer = diningOffer;
	}


}
