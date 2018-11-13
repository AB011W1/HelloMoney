package com.barclays.bmg.mvc.controller.accountsummary;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.accountdetails.CreditCardLinkConfirmCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accounts.CreditCardLinkConfirmOperation;
import com.barclays.bmg.operation.accounts.request.CreditCardLinkConfirmOperationRequest;
import com.barclays.bmg.operation.accounts.request.CreditCardLinkConfirmOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class CreditCardLinkConfirmController extends
BMBAbstractCommandController {

	private CreditCardLinkConfirmOperation creditCardLinkConfirmOperation;
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
		CreditCardLinkConfirmCommand creditCardLinkConfirmCommand = (CreditCardLinkConfirmCommand) command;
		CreditCardLinkConfirmOperationRequest creditCardLinkConfirmOperationRequest = makeRequest(httpRequest);

		creditCardLinkConfirmOperationRequest.setPrimaryAccountNumber(creditCardLinkConfirmCommand.getPrimaryAccountNumber());
		creditCardLinkConfirmOperationRequest.setMobileNumber(creditCardLinkConfirmCommand.getMobileNumber());

		CreditCardLinkConfirmOperationResponse creditCardLinkConfirmOperationResponse= creditCardLinkConfirmOperation
		.creditCardLink(creditCardLinkConfirmOperationRequest);
		return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(creditCardLinkConfirmOperationResponse);

	}

	private CreditCardLinkConfirmOperationRequest makeRequest(
			HttpServletRequest request) {
		String opCde = request.getParameter("opCde");
		CreditCardLinkConfirmOperationRequest creditCardLinkConfirmOperationRequest = new CreditCardLinkConfirmOperationRequest();
		Context context = createContext(request);
		context
		.setActivityId(ActivityConstant.CREDIT_CARD_LINK_ACTIVITY_ID);
		context.setActivityRefNo(BMBCommonUtility.generateRefNo());
		context.setOpCde(opCde);
		creditCardLinkConfirmOperationRequest.setContext(context);
		return creditCardLinkConfirmOperationRequest;
	}

	public void setCreditCardLinkConfirmOperation(
			CreditCardLinkConfirmOperation creditCardLinkConfirmOperation) {
		this.creditCardLinkConfirmOperation = creditCardLinkConfirmOperation;
	}

	public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
		this.bmbJsonBuilder = bmbJsonBuilder;
	}

	@Override
	protected String getActivityId(Object command) {
		return ActivityConstant.CREDIT_CARD_LINK_ACTIVITY_ID;
	}

}
