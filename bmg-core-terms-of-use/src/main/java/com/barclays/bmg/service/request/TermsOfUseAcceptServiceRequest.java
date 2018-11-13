package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;

public class TermsOfUseAcceptServiceRequest extends RequestContext {
	private String termsOfUseVersionNo;
	private String acceptFlag;

	public String getTermsOfUseVersionNo() {
		return termsOfUseVersionNo;
	}

	public void setTermsOfUseVersionNo(String termsOfUseVersionNo) {
		this.termsOfUseVersionNo = termsOfUseVersionNo;
	}

	public String getAcceptFlag() {
		return acceptFlag;
	}

	public void setAcceptFlag(String acceptFlag) {
		this.acceptFlag = acceptFlag;
	}


}
