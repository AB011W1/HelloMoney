package com.barclays.bmg.groupwallet.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.pesalink.RetrieveCASAAcctTranActivityCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.RetrevCASAAcctTranActivityListOperation;
import com.barclays.bmg.service.accounts.request.RetrevCASAAcctTranActivityOperationRequest;
import com.barclays.bmg.service.accounts.response.RetrevCASAAcctTranActivityOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class RetrevCASAAcctTranActivityController extends BMBAbstractCommandController {
	RetrevCASAAcctTranActivityListOperation retrevCASAAcctTranActivityListOperation;
	BMBJSONBuilder retrevCASAAcctTranActivityJsonBldr;
	private String activityId;

	public RetrevCASAAcctTranActivityListOperation getRetrevCASAAcctTranActivityListOperation() {
		return retrevCASAAcctTranActivityListOperation;
	}

	public void setRetrevCASAAcctTranActivityListOperation(
			RetrevCASAAcctTranActivityListOperation retrevCASAAcctTranActivityListOperation) {
		this.retrevCASAAcctTranActivityListOperation = retrevCASAAcctTranActivityListOperation;
	}

	public BMBJSONBuilder getRetrevCASAAcctTranActivityJsonBldr() {
		return retrevCASAAcctTranActivityJsonBldr;
	}

	public void setRetrevCASAAcctTranActivityJsonBldr(
			BMBJSONBuilder retrevCASAAcctTranActivityJsonBldr) {
		this.retrevCASAAcctTranActivityJsonBldr = retrevCASAAcctTranActivityJsonBldr;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}


	protected String getActivityId() {
		// TODO Auto-generated method stub
		return activityId;
	}

	private RetrevCASAAcctTranActivityOperationRequest makeRequest(
			HttpServletRequest request, RetrieveCASAAcctTranActivityCommand cmd) {

		Context context = createContext(request);
		context.getContextMap().put("inputMobileNumber",
				(String) request.getParameter("mobileNo"));
		context.setActivityId(request.getParameter("activityId"));
		context.setActivityRefNo(BMBCommonUtility.generateRefNo());
		context.setOpCde(request.getParameter("opCde"));
		RetrevCASAAcctTranActivityOperationRequest retrevCASAAcctTranActivityOperationRequest = new RetrevCASAAcctTranActivityOperationRequest();

		retrevCASAAcctTranActivityOperationRequest.setContext(context);
		retrevCASAAcctTranActivityOperationRequest.setActionCode(cmd.getActionCode());
		retrevCASAAcctTranActivityOperationRequest.setUserID(context.getCustomerId());
		retrevCASAAcctTranActivityOperationRequest.setAccountNumber(cmd.getAccNo());
		retrevCASAAcctTranActivityOperationRequest.setBranchCode(cmd.getBranchCode());
		retrevCASAAcctTranActivityOperationRequest.setTransactionStatus(cmd.getTransactionStatus());
		retrevCASAAcctTranActivityOperationRequest.setTrxReferenceNumber(cmd.getTransactionNumber());
		retrevCASAAcctTranActivityOperationRequest.setLockRequired(cmd.getLockRequired());
		retrevCASAAcctTranActivityOperationRequest.setTranTypeCode(cmd.getTranTypeCode());

		return retrevCASAAcctTranActivityOperationRequest;
	}

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		// TODO Auto-generated method stub
		RetrieveCASAAcctTranActivityCommand  cmd=(RetrieveCASAAcctTranActivityCommand)command;
		RetrevCASAAcctTranActivityOperationRequest retrevCASAAcctTranActivityOperationRequest = makeRequest(request,cmd);
		RetrevCASAAcctTranActivityOperationResponse retrevCASAAcctTranActivityOperationResponse =retrevCASAAcctTranActivityListOperation.retrevCasaAcctTranActivity(retrevCASAAcctTranActivityOperationRequest);
		return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) retrevCASAAcctTranActivityJsonBldr)
		.createMultipleJSONResponse(retrevCASAAcctTranActivityOperationResponse);
	}


}
