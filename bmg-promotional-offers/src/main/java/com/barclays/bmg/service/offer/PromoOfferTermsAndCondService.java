package com.barclays.bmg.service.offer;

import com.barclays.bmg.service.request.offer.TermsAndCondServiceRequest;
import com.barclays.bmg.service.response.offer.TermsAndCondServiceResponse;

public interface PromoOfferTermsAndCondService {
	TermsAndCondServiceResponse retrieveTermsAndCondition(TermsAndCondServiceRequest request);
}
