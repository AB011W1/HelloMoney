package com.barclays.bmg.service.request.pesalink;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.service.request.ReportProblemServiceRequest;
import com.barclays.bmg.service.response.ReportProblemServiceResponse;



public class SearchIndividualCustforDeDupCheckServiceRequest extends RequestContext{

	private String mobileNumber;

	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


}


