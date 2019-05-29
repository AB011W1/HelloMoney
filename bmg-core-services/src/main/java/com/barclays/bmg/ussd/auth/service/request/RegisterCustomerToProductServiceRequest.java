package com.barclays.bmg.ussd.auth.service.request;

import com.barclays.bmg.context.RequestContext;

public class RegisterCustomerToProductServiceRequest extends RequestContext{

	private String beneficiaryLastName;
	private String beneficiaryFirstName;

	public String getBeneficiaryLastName() {
		return beneficiaryLastName;
	}
	public void setBeneficiaryLastName(String beneficiaryLastName) {
		this.beneficiaryLastName = beneficiaryLastName;
	}
	public String getBeneficiaryFirstName() {
		return beneficiaryFirstName;
	}
	public void setBeneficiaryFirstName(String beneficiaryFirstName) {
		this.beneficiaryFirstName = beneficiaryFirstName;
	}




}
