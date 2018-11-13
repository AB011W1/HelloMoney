package com.barclays.bmg.service.response.offer;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.offer.EIPOfferDTO;

public class EIPOfferDetailServiceResponse extends ResponseContext {

	private static final long serialVersionUID = 1139224284975514761L;

	private EIPOfferDTO eipOffer;

	public EIPOfferDTO getEipOffer() {
		return eipOffer;
	}

	public void setEipOffer(EIPOfferDTO eipOffer) {
		this.eipOffer = eipOffer;
	}




}
