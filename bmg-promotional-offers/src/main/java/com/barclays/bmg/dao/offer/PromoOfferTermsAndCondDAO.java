package com.barclays.bmg.dao.offer;

import com.barclays.bmg.service.request.offer.TermsAndCondServiceRequest;
import com.barclays.bmg.service.response.offer.TermsAndCondServiceResponse;

public interface PromoOfferTermsAndCondDAO {
	TermsAndCondServiceResponse retrieveTermsAndCondition(TermsAndCondServiceRequest request);
}
