package com.barclays.bmg.mvc.controller.accountsummary;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.accountdetails.ApplyProductConfirmCommand;

import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accounts.ApplyProductConfirmOperation;

import com.barclays.bmg.operation.accounts.request.ApplyProductConfirmOperationRequest;
import com.barclays.bmg.operation.accounts.request.ApplyProductConfirmOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.AddOrgBeneficiaryOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class ApplyProductConfirmController extends
BMBAbstractCommandController {

	private ApplyProductConfirmOperation applyProductConfirmOperation;
	private BMBJSONBuilder bmbJsonBuilder;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.validation.BindException)
	 */
	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse response, Object command, BindException errors)
	throws Exception {
		ApplyProductConfirmCommand applyProductConfirmCommand = (ApplyProductConfirmCommand) command;
		ApplyProductConfirmOperationRequest applyProductConfirmOperationRequest = makeRequest(httpRequest);

		applyProductConfirmOperationRequest.setPrimaryAccountNumber(applyProductConfirmCommand.getPrimaryAccountNumber());
		applyProductConfirmOperationRequest.setMobileNumber(applyProductConfirmCommand.getMobileNumber());
		ApplyProductConfirmOperationResponse applyProductConfirmOperationResponse = new ApplyProductConfirmOperationResponse();

		applyProductConfirmOperationResponse= applyProductConfirmOperation.applyProduct(applyProductConfirmOperationRequest);

		/* Code starts for SMS */
		if (applyProductConfirmOperationResponse != null)
				applyProductConfirmOperation.sendSMSSuccessAcknowledgment(applyProductConfirmOperationRequest, applyProductConfirmOperationResponse);
		/* Code ends for SMS */

		return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(applyProductConfirmOperationResponse);

	}

	private ApplyProductConfirmOperationRequest makeRequest(
			HttpServletRequest request) {

		ApplyProductConfirmOperationRequest applyProductConfirmOperationRequest = new ApplyProductConfirmOperationRequest();
		Context context = createContext(request);

		context
		.setActivityId(ActivityConstant.APPLY_PRODUCT_ACTIVITY_ID);
		context.setActivityRefNo(BMBCommonUtility.generateRefNo());
		context.setOpCde(request.getParameter("opCde"));
		applyProductConfirmOperationRequest.setContext(context);
		applyProductConfirmOperationRequest.setProductName(request.getParameter("productName"));
		applyProductConfirmOperationRequest.setSubProductName(request.getParameter("subProductName"));
		return applyProductConfirmOperationRequest;
	}

	public void setApplyProductConfirmOperation(
			ApplyProductConfirmOperation applyProductConfirmOperation) {
		this.applyProductConfirmOperation = applyProductConfirmOperation;
	}

	public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
		this.bmbJsonBuilder = bmbJsonBuilder;
	}

	@Override
	protected String getActivityId(Object command) {
		return ActivityConstant.APPLY_PRODUCT_ACTIVITY_ID;
	}

}
