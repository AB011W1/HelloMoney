package com.barclays.bmg.mvc.controller.billpayment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountdetails.response.ApplyTDOperationResponse;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
public class ApplyTDInitController  extends BMBAbstractCommandController {

	private  RetrieveAccountListOperation retrieveAccountListOperation;

	public RetrieveAccountListOperation getRetrieveAccountListOperation() {
		return retrieveAccountListOperation;
	}



	public void setRetrieveAccountListOperation(
			RetrieveAccountListOperation retrieveAccountListOperation) {
		this.retrieveAccountListOperation = retrieveAccountListOperation;
	}

	private BMBJSONBuilder bmbJSONBuilder;

	/* (non-Javadoc)
	 * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 *
	 * 1. Retrieve TD Account Details
	 * 2. make JSON response
	 */
	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command,
			BindException errors) throws Exception {

		ApplyTDOperationResponse  applyTDOpResponse = new ApplyTDOperationResponse();
 		RetrieveAcctListOperationRequest makeRequest = makeRequest(httpRequest);
	    RetrieveAcctListOperationResponse retrieveAccListOperationResponse = retrieveAccountListOperation.getCASASourceAccounts(makeRequest);
		applyTDOpResponse.setSourceAccList(retrieveAccListOperationResponse.getAcctList());

		mapCorrelationIds(retrieveAccListOperationResponse.getAcctList(), makeRequest,
				httpRequest,retrieveAccListOperationResponse, BMGProcessConstants.APPLY_TD);
         applyTDOpResponse.setContext(retrieveAccListOperationResponse.getContext());
		return (BMBBaseResponseModel) bmbJSONBuilder
				.createJSONResponse(applyTDOpResponse);
	}



	/**
	 * make  TDAccountDetailsOperationRequest
	 *
	 * @param httpRequest
	 * @return
	 */
	private RetrieveAcctListOperationRequest makeRequest(
			HttpServletRequest httpRequest) {
		Context context = createContext(httpRequest);
		RetrieveAcctListOperationRequest request = new RetrieveAcctListOperationRequest();

		context.setActivityId(ActivityConstant.OPEN_TERMDEPOSIT_ACTIVITY_ID);
		request.setContext(context);

		return request;
	}

	@Override
	protected String getActivityId(Object command) {

		return ActivityConstant.OPEN_TERMDEPOSIT_ACTIVITY_ID;
	}

	public BMBJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}
}
