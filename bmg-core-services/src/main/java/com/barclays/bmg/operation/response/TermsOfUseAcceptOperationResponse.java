package com.barclays.bmg.operation.response;

import com.barclays.bmg.context.ResponseContext;

public class TermsOfUseAcceptOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 4598816431570213361L;
    private String termsAndCondition;

    public String getTermsAndCondition() {
	return termsAndCondition;
    }

    public void setTermsAndCondition(String termsAndCondition) {
	this.termsAndCondition = termsAndCondition;
    }
}