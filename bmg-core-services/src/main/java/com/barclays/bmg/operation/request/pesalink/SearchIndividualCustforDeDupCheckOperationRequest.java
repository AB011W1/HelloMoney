package com.barclays.bmg.operation.request.pesalink;

import com.barclays.bmg.context.RequestContext;

public class SearchIndividualCustforDeDupCheckOperationRequest extends RequestContext {


	private String mobileNumber;

	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


}
