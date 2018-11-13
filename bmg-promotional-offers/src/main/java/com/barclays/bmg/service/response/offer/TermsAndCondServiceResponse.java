package com.barclays.bmg.service.response.offer;

import com.barclays.bmg.context.ResponseContext;

public class TermsAndCondServiceResponse extends ResponseContext {

    private String termsAndCondition;

    public String getTermsAndCondition() {
	return termsAndCondition;
    }

    public void setTermsAndCondition(String termsAndCondition) {
	this.termsAndCondition = termsAndCondition;
    }

}
