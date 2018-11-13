package com.barclays.bmg.operation.offer.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.offer.EIPOfferDTO;

public class EIPOfferDetailOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 1673643261925515156L;
    private EIPOfferDTO eipOffer;

    public EIPOfferDTO getEipOffer() {
	return eipOffer;
    }

    public void setEipOffer(EIPOfferDTO eipOffer) {
	this.eipOffer = eipOffer;
    }
}