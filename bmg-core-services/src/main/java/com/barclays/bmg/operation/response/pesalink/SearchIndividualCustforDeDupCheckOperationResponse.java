package com.barclays.bmg.operation.response.pesalink;


import java.util.List;

import com.barclays.bmg.context.ResponseContext;

public class SearchIndividualCustforDeDupCheckOperationResponse extends ResponseContext {

	private List <IndividualCustomerBasicOpRes>	individualCustomerBasicOpResList;

	//Required for Deregistration
	private IndividualCustAdditionalInfoOpRes  individualCustAdditionalInfoOpRes;


	public List<IndividualCustomerBasicOpRes> getIndividualCustomerBasicOpResList() {
		return individualCustomerBasicOpResList;
	}

	public void setIndividualCustomerBasicOpResList(
			List<IndividualCustomerBasicOpRes> individualCustomerBasicOpResList) {
		this.individualCustomerBasicOpResList = individualCustomerBasicOpResList;
	}

	public IndividualCustAdditionalInfoOpRes getIndividualCustAdditionalInfoOpRes() {
		return individualCustAdditionalInfoOpRes;
	}

	public void setIndividualCustAdditionalInfoOpRes(
			IndividualCustAdditionalInfoOpRes individualCustAdditionalInfoOpRes) {
		this.individualCustAdditionalInfoOpRes = individualCustAdditionalInfoOpRes;
	}


}
