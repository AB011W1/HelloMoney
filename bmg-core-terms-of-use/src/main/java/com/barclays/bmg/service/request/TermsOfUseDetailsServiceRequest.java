package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class TermsOfUseDetailsServiceRequest extends RequestContext {
	private String termsOfUseVersionNo;

	public String getTermsOfUseVersionNo() {
		return termsOfUseVersionNo;
	}

	public void setTermsOfUseVersionNo(String termsOfUseVersionNo) {
		this.termsOfUseVersionNo = termsOfUseVersionNo;
	}


}
