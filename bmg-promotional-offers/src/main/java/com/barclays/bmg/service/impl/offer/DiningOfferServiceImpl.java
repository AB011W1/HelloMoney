package com.barclays.bmg.service.impl.offer;

import com.barclays.bmg.dao.offer.DiningOfferDao;
import com.barclays.bmg.service.offer.DiningOfferService;
import com.barclays.bmg.service.request.offer.DiningOfferDetailsServiceRequest;
import com.barclays.bmg.service.request.offer.DiningServiceRequest;
import com.barclays.bmg.service.response.offer.DiningOfferDetailsServiceResponse;
import com.barclays.bmg.service.response.offer.DiningServiceResponse;

public class DiningOfferServiceImpl implements DiningOfferService{

	private DiningOfferDao diningOfferDao;

	public DiningServiceResponse retrieveAllDiningOffers(
			DiningServiceRequest request) {
		// TODO Auto-generated method stub

		return diningOfferDao.retrieveAllDiningOffers(request);
	}

	public DiningOfferDetailsServiceResponse retrieveDiningOfferDetail(
			DiningOfferDetailsServiceRequest request) {
		// TODO Auto-generated method stub
		return diningOfferDao.retrieveDiningOfferDetail(request);
	}

	public DiningServiceResponse searchDiningOffers(DiningServiceRequest request) {
		// TODO Auto-generated method stub
		return diningOfferDao.searchDiningOffers(request);
	}

	@Override
	public DiningServiceResponse retrieveAllCities(DiningServiceRequest request) {
		// TODO Auto-generated method stub
		return diningOfferDao.retrieveAllCities(request);
	}

	public void setDiningOfferDao(DiningOfferDao diningOfferDao) {
		this.diningOfferDao = diningOfferDao;
	}







}
