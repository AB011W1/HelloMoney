package com.barclays.bmg.service.offer;

import com.barclays.bmg.service.request.offer.EIPOfferDetailServiceRequest;
import com.barclays.bmg.service.request.offer.EIPServiceRequest;
import com.barclays.bmg.service.response.offer.EIPOfferDetailServiceResponse;
import com.barclays.bmg.service.response.offer.EIPServiceResponse;

public interface EIPOfferService {
	public EIPServiceResponse retrieveAllEipOffers(EIPServiceRequest request);

	public EIPOfferDetailServiceResponse retrieveEipOfferDetails(EIPOfferDetailServiceRequest request);

	public EIPServiceResponse searchEipOffers(EIPServiceRequest request);
	public EIPServiceResponse retrieveAllCategories(EIPServiceRequest request);

}
