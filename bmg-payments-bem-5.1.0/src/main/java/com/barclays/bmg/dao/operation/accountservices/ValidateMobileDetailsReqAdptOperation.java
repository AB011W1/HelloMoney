package com.barclays.bmg.dao.operation.accountservices;


import com.barclays.bem.RetrieveIndividualCustAcctBasic.RetrieveIndividualCustAcctBasicRequest;
import com.barclays.bem.RetrieveMobileDetails.RetrieveMobileDetailsRequest;
import com.barclays.bmg.dao.accountservices.adapter.ValidateMobileDetailsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.ValidateMobileDetailsPayLoadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class ValidateMobileDetailsReqAdptOperation {
	
	private ValidateMobileDetailsHeaderAdapter validateMobileDetailsHeaderAdapter;
	private ValidateMobileDetailsPayLoadAdapter validateMobileDetailsPayLoadAdapter;
	public ValidateMobileDetailsHeaderAdapter getValidateMobileDetailsHeaderAdapter() {
		return validateMobileDetailsHeaderAdapter;
	}

	public void setValidateMobileDetailsHeaderAdapter(
			ValidateMobileDetailsHeaderAdapter validateMobileDetailsHeaderAdapter) {
		this.validateMobileDetailsHeaderAdapter = validateMobileDetailsHeaderAdapter;
	}

	public ValidateMobileDetailsPayLoadAdapter getValidateMobileDetailsPayLoadAdapter() {
		return validateMobileDetailsPayLoadAdapter;
	}

	public void setValidateMobileDetailsPayLoadAdapter(
			ValidateMobileDetailsPayLoadAdapter validateMobileDetailsPayLoadAdapter) {
		this.validateMobileDetailsPayLoadAdapter = validateMobileDetailsPayLoadAdapter;
	}

	
	
	//TODO Service request and response 
	public RetrieveMobileDetailsRequest adaptRequest(WorkContext workContext) {
		RetrieveMobileDetailsRequest request = new RetrieveMobileDetailsRequest();

		request.setRequestHeader(validateMobileDetailsHeaderAdapter.buildBemReqHeader(workContext));
		request.setIndividualCustomerInfo(validateMobileDetailsPayLoadAdapter.adaptPayLoad(workContext));

		return request;
	}

}
