package com.barclays.bmg.mvc.controller.billpayment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ApplyTDConstants;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.ApplyTDAddProblemCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountdetails.request.ApplyTDAddProblemOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.ApplyTDValidationOperationResponse;
import com.barclays.bmg.service.response.ApplyTDServiceResponse;

public class ApplyTDExecutionController extends BMBAbstractCommandController {

	private BMBJSONBuilder bmbJSONBuilder;
	private com.barclays.bmg.dao.operation.ApplyTDAddProbmlemOperation applyTDAddProbmlemOperation;

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,HttpServletResponse httpResponse, Object command,
			                                                                               BindException errors) throws Exception {
		ApplyTDAddProblemCommand inputCommand = (ApplyTDAddProblemCommand) command;
		ApplyTDAddProblemOperationRequest applTDAddPrbRequest = makeRequest(httpRequest);
		ApplyTDValidationOperationResponse applyTDValidationOperationResponse
		                            =(ApplyTDValidationOperationResponse) getFromProcessMap(httpRequest, BMGProcessConstants.APPLY_TD, inputCommand.getTxnRefNo());

		addNotesDetailsToRequest(applTDAddPrbRequest,applyTDValidationOperationResponse);
		ApplyTDServiceResponse applyTDAddProblemResponse = applyTDAddProbmlemOperation.applyTDAddProblem(applTDAddPrbRequest,
						                                                              applyTDValidationOperationResponse);
		return (BMBBaseResponseModel) bmbJSONBuilder.createJSONResponse(applyTDAddProblemResponse);
	}

	private void addNotesDetailsToRequest(
			ApplyTDAddProblemOperationRequest applTDAddPrbRequest,
			ApplyTDValidationOperationResponse applyTDValidationOperationResponse) {
		CustomerAccountDTO sourceCustomerAccountDTO = applyTDValidationOperationResponse
				.getSourceCustomerAccountDTO();
		String accountType = sourceCustomerAccountDTO.getAccountType();
		String accountNumber = sourceCustomerAccountDTO.getAccountNumber();
		String tenureMonths = applyTDValidationOperationResponse.getTenureMonths();
		String tenureDays = applyTDValidationOperationResponse.getTenureDays();


		String amount = applyTDValidationOperationResponse.getDepositAmount();
		String productId = applyTDValidationOperationResponse.getProductId();

		Context context = applTDAddPrbRequest.getContext();
		String localCurrency = context.getLocalCurrency();
		StringBuffer noteDescription = new StringBuffer();

		noteDescription.append("Source Account Number: {" + accountNumber + " "+ accountType + " },");
		noteDescription.append("Product Id: {" + productId + "},");
		noteDescription.append("Tenure: {" + tenureMonths + " months,"+tenureDays +" days},");
		noteDescription.append("Amount: {" + amount + " " + localCurrency + "}");
		applTDAddPrbRequest.setNoteDescription(noteDescription.toString());

		applTDAddPrbRequest.setNotesId(accountNumber);
		applTDAddPrbRequest.setObjectId(accountNumber);
		applTDAddPrbRequest.setSubject(ApplyTDConstants.NOTES_SUBJECT);

	}

	@Override
	protected String getActivityId(Object command) {

		return ActivityConstant.TD_ACCOUNT_DETAILS;
	}

	/**
	 * make TDAccountDetailsOperationRequest
	 *
	 * @param httpRequest
	 * @return
	 */
	private ApplyTDAddProblemOperationRequest makeRequest(
			HttpServletRequest httpRequest) {

		Context context = createContext(httpRequest);
		ApplyTDAddProblemOperationRequest request = new ApplyTDAddProblemOperationRequest();
		context.setActivityId(ActivityConstant.OPEN_TERMDEPOSIT_ACTIVITY_ID);
		// Set context parameters bank,customer
		// setBankingContext(request);

		request.setContext(context);

		return request;
	}



	public BMBJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public com.barclays.bmg.dao.operation.ApplyTDAddProbmlemOperation getApplyTDAddProbmlemOperation() {
		return applyTDAddProbmlemOperation;
	}

	public void setApplyTDAddProbmlemOperation(
			com.barclays.bmg.dao.operation.ApplyTDAddProbmlemOperation applyTDAddProbmlemOperation) {
		this.applyTDAddProbmlemOperation = applyTDAddProbmlemOperation;
	}

	/**
	 * @param request
	 * @param httpRequest
	 *            add required parameters to context from session Map.
	 */
	private void addContext(RequestContext request,
			HttpServletRequest httpRequest, String activityId) {
		Context context = createContext(httpRequest);
		context.setActivityId(activityId);
		request.setContext(context);
	}

}
