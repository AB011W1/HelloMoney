package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.TermsOfUseDAO;
import com.barclays.bmg.service.TermsOfUseService;
import com.barclays.bmg.service.request.TermsOfUseAcceptServiceRequest;
import com.barclays.bmg.service.request.TermsOfUseDetailsServiceRequest;
import com.barclays.bmg.service.response.TermsOfUseAcceptServiceResponse;
import com.barclays.bmg.service.response.TermsOfUseDetailsServiceResponse;

public class TermsOfUseServiceImpl implements TermsOfUseService {

	private TermsOfUseDAO termsOfUseDAO;

	/*@Override
	public TermsOfUseDetailsServiceResponse retrieveTermsOfUseDetails(
			TermsOfUseDetailsServiceRequest request) {

		return termsOfUseDAO.retrieveTermsOfUseDetails(request);
	}*/

	@Override
	public TermsOfUseAcceptServiceResponse acceptTermsOfUse(
			TermsOfUseAcceptServiceRequest request) {

		return termsOfUseDAO.acceptTermsOfUse(request);
	}


	@Override
	public TermsOfUseAcceptServiceResponse checkTermsOfUseAccept(
			TermsOfUseAcceptServiceRequest request) {

		return termsOfUseDAO.checkTermsOfUseAccept(request);
	}

	public void setTermsOfUseDAO(TermsOfUseDAO termsOfUseDAO) {
		this.termsOfUseDAO = termsOfUseDAO;
	}



}
