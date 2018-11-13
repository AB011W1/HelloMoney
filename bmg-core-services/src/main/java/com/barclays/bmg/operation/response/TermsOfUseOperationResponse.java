package com.barclays.bmg.operation.response;

import com.barclays.bmg.context.ResponseContext;

public class TermsOfUseOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 3147925861965673191L;
    private String termsAndCondition;

    public String getTermsAndCondition() {
	return termsAndCondition;
    }

    public void setTermsAndCondition(String termsAndCondition) {
	this.termsAndCondition = termsAndCondition;
    }
}