package com.barclays.bmg.service.response.pesalink;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;




public class SearchIndividualCustforDeDupCheckServiceResponse extends ResponseContext {

	private List <IndividualCustomerBasicSerRes>	individualCustomerBasicSerResList;

	//Required for Deregistration
	private IndividualCustAdditionalInfoSerRes  individualCustAdditionalInfoSerRes;


	public List<IndividualCustomerBasicSerRes> getIndividualCustomerBasicSerResList() {
		return individualCustomerBasicSerResList;
	}

	public void setIndividualCustomerBasicSerResList(
			List<IndividualCustomerBasicSerRes> individualCustomerBasicSerResList) {
		this.individualCustomerBasicSerResList = individualCustomerBasicSerResList;
	}

	public IndividualCustAdditionalInfoSerRes getIndividualCustAdditionalInfoSerRes() {
		return individualCustAdditionalInfoSerRes;
	}

	public void setIndividualCustAdditionalInfoSerRes(
			IndividualCustAdditionalInfoSerRes individualCustAdditionalInfoSerRes) {
		this.individualCustAdditionalInfoSerRes = individualCustAdditionalInfoSerRes;
	}


}


