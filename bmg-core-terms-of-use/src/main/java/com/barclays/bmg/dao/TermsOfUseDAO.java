package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.TermsOfUseAcceptServiceRequest;
import com.barclays.bmg.service.request.TermsOfUseDetailsServiceRequest;
import com.barclays.bmg.service.response.TermsOfUseAcceptServiceResponse;
import com.barclays.bmg.service.response.TermsOfUseDetailsServiceResponse;

public interface TermsOfUseDAO {
	/*public TermsOfUseDetailsServiceResponse retrieveTermsOfUseDetails(TermsOfUseDetailsServiceRequest request);*/

	public TermsOfUseAcceptServiceResponse acceptTermsOfUse(TermsOfUseAcceptServiceRequest request);

	public TermsOfUseAcceptServiceResponse checkTermsOfUseAccept(TermsOfUseAcceptServiceRequest request);
}
