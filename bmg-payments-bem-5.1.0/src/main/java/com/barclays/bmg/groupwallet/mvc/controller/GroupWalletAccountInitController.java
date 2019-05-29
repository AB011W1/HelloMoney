package com.barclays.bmg.groupwallet.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.operation.accountservices.RetrieveGroupWalletAccountListOperation;
import com.barclays.bmg.service.accounts.request.AllGroupWalletAccountOperationRequest;
import com.barclays.bmg.service.accounts.response.AllGroupWalletAccountOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class GroupWalletAccountInitController extends BMBAbstractController {

	RetrieveGroupWalletAccountListOperation retrieveGroupWalletAccountListOperation;
	BMBJSONBuilder groupWalletOneTimeJsonBldr;
	String activityId;
	private static String CUSTOMER_TYPE = "AUS";
	private static String ACTION_CODE = "ActionCode";
	private static String ACTION_CODE_VALUE = "FILTEREDLIST";


	public RetrieveGroupWalletAccountListOperation getRetrieveGroupWalletAccountListOperation() {
		return retrieveGroupWalletAccountListOperation;
	}

	public void setRetrieveGroupWalletAccountListOperation(
			RetrieveGroupWalletAccountListOperation retrieveGroupWalletAccountListOperation) {
		this.retrieveGroupWalletAccountListOperation = retrieveGroupWalletAccountListOperation;
	}

	public BMBJSONBuilder getGroupWalletOneTimeJsonBldr() {
		return groupWalletOneTimeJsonBldr;
	}

	public void setGroupWalletOneTimeJsonBldr(
			BMBJSONBuilder groupWalletOneTimeJsonBldr) {
		this.groupWalletOneTimeJsonBldr = groupWalletOneTimeJsonBldr;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	@Override
	protected String getActivityId() {
		// TODO Auto-generated method stub
		return activityId;
	}

	@Override
	protected BMBBaseResponseModel handleRequestInternal1(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		AllGroupWalletAccountOperationRequest allGroupWalletAccountServiceRequest = makeRequest(request);
		AllGroupWalletAccountOperationResponse allGroupWalletAccountOperationResponse =retrieveGroupWalletAccountListOperation.getAllGroupWalletAccountList(allGroupWalletAccountServiceRequest);
		return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) groupWalletOneTimeJsonBldr)
		.createMultipleJSONResponse(allGroupWalletAccountOperationResponse);
	}

	private AllGroupWalletAccountOperationRequest makeRequest(
			HttpServletRequest request) {

		Context context = createContext(request);
		context.getContextMap().put("inputMobileNumber",
				(String) request.getParameter("mobileNo"));
		context.setActivityId(request.getParameter("activityId"));
		context.setActivityRefNo(BMBCommonUtility.generateRefNo());
		context.setOpCde(request.getParameter("opCde"));
		AllGroupWalletAccountOperationRequest allGroupWalletAccountServiceRequest = new AllGroupWalletAccountOperationRequest();

		allGroupWalletAccountServiceRequest.setContext(context);
		allGroupWalletAccountServiceRequest.setCustomerID(context
				.getCustomerId());
		allGroupWalletAccountServiceRequest.setCustomerType(CUSTOMER_TYPE);
		allGroupWalletAccountServiceRequest.setActionCode(ACTION_CODE);
		allGroupWalletAccountServiceRequest
				.setActionCodeValue(ACTION_CODE_VALUE);
		return allGroupWalletAccountServiceRequest;
	}

}