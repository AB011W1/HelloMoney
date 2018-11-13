package com.barclays.bmg.service.request.pesalink;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.service.request.ReportProblemServiceRequest;
import com.barclays.bmg.service.response.ReportProblemServiceResponse;



public class CreateIndividualCustomerServiceRequest extends RequestContext{

	private String mobileNumber;
	private String accountNumber;
	private Boolean primaryFlag;

	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Boolean getPrimaryFlag() {
		return primaryFlag;
	}
	public void setPrimaryFlag(Boolean primaryFlag) {
		this.primaryFlag = primaryFlag;
	}

}


