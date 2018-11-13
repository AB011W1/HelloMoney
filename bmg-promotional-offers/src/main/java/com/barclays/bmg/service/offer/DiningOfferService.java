package com.barclays.bmg.service.offer;

import com.barclays.bmg.service.request.offer.DiningOfferDetailsServiceRequest;
import com.barclays.bmg.service.request.offer.DiningServiceRequest;
import com.barclays.bmg.service.response.offer.DiningOfferDetailsServiceResponse;
import com.barclays.bmg.service.response.offer.DiningServiceResponse;

public interface DiningOfferService {
	public DiningServiceResponse retrieveAllDiningOffers(DiningServiceRequest request);
	public DiningOfferDetailsServiceResponse retrieveDiningOfferDetail(DiningOfferDetailsServiceRequest request);
	public DiningServiceResponse searchDiningOffers(DiningServiceRequest request);
	public DiningServiceResponse retrieveAllCities(DiningServiceRequest request);

}