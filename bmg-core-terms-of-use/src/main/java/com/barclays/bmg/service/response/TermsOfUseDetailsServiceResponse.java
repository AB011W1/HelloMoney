package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;

public class TermsOfUseDetailsServiceResponse extends ResponseContext {

	private String termsAndCondition;

	public String getTermsAndCondition() {
		return termsAndCondition;
	}

	public void setTermsAndCondition(String termsAndCondition) {
		this.termsAndCondition = termsAndCondition;
	}
}
