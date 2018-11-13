package com.barclays.bmg.service.impl.offer;

import com.barclays.bmg.dao.offer.PromoOfferTermsAndCondDAO;
import com.barclays.bmg.service.offer.PromoOfferTermsAndCondService;
import com.barclays.bmg.service.request.offer.TermsAndCondServiceRequest;
import com.barclays.bmg.service.response.offer.TermsAndCondServiceResponse;

public class PromoOfferTermsAndCondServiceImpl implements
		PromoOfferTermsAndCondService {

	private PromoOfferTermsAndCondDAO promoOfferTermsAndCondDAO;
	@Override
	public TermsAndCondServiceResponse retrieveTermsAndCondition(
			TermsAndCondServiceRequest request) {
		// TODO Auto-generated method stub
		return promoOfferTermsAndCondDAO.retrieveTermsAndCondition(request);
	}
	public void setPromoOfferTermsAndCondDAO(
			PromoOfferTermsAndCondDAO promoOfferTermsAndCondDAO) {
		this.promoOfferTermsAndCondDAO = promoOfferTermsAndCondDAO;
	}



}
