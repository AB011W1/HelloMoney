package com.barclays.bmg.service.impl.offer;

import com.barclays.bmg.dao.offer.EIPOfferDao;
import com.barclays.bmg.service.offer.EIPOfferService;
import com.barclays.bmg.service.request.offer.EIPOfferDetailServiceRequest;
import com.barclays.bmg.service.request.offer.EIPServiceRequest;
import com.barclays.bmg.service.response.offer.EIPOfferDetailServiceResponse;
import com.barclays.bmg.service.response.offer.EIPServiceResponse;

public class EIPOfferServiceImpl implements EIPOfferService{

	private EIPOfferDao	eipOfferDao;



	public EIPServiceResponse retrieveAllEipOffers(EIPServiceRequest request) {
		return eipOfferDao.retrieveAllEipOffers(request);
	}

	public EIPOfferDetailServiceResponse retrieveEipOfferDetails(EIPOfferDetailServiceRequest request) {
			return eipOfferDao.retrieveEipOfferDetails(request);
	}

	public EIPServiceResponse searchEipOffers(EIPServiceRequest request) {
		return eipOfferDao.searchEipOffers(request);
	}

	@Override
	public EIPServiceResponse retrieveAllCategories(EIPServiceRequest request) {
		return eipOfferDao.retrieveAllCategories(request);
	}

	public void setEipOfferDao(EIPOfferDao eipOfferDao) {
		this.eipOfferDao = eipOfferDao;
	}








}
