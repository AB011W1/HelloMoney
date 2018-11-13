package com.barclays.bmg.mvc.controller.billpayment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ApplyTDConstants;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.ApplyTDSourceAccountDetailsCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountdetails.request.ApplyTDValidationOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.ApplyTDValidationOperationResponse;

public class ApplyTDValidationController1 extends BMBAbstractCommandController {

	private BMBJSONBuilder bmbJSONBuilder;
	private com.barclays.bmg.dao.operation.ApplyTDValidateOperation applyTDValidateOperation;

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command,
			BindException errors) throws Exception {

		ApplyTDSourceAccountDetailsCommand inputForApplyTDCommand = (ApplyTDSourceAccountDetailsCommand) command;
		ApplyTDValidationOperationRequest applyTDvalidationsOpReq = makeRequest(httpRequest);
		Context context = createContext(httpRequest);
		context.setActivityId(getActivityId(inputForApplyTDCommand));
		// Take care of acct No validation in Command Validator.
		String actNo = inputForApplyTDCommand.getActNo();
		applyTDvalidationsOpReq.setAcctNo(getAccountNumber(actNo, httpRequest, BMGProcessConstants.APPLY_TD));
		applyTDvalidationsOpReq.setDepositAmount(inputForApplyTDCommand.getDepositAmount());
		applyTDvalidationsOpReq.setTenureMonths(inputForApplyTDCommand.getTenureMonths());
		applyTDvalidationsOpReq.setTenureDays(inputForApplyTDCommand.getTenureDays());
		applyTDvalidationsOpReq.setProductId(inputForApplyTDCommand.getProductId());

		ApplyTDValidationOperationResponse validateResponse = applyTDValidateOperation.validateInputs(applyTDvalidationsOpReq);

		if (validateResponse.isSuccess()) {
			validateResponse = applyTDValidateOperation .getSourceAccountDetails(applyTDvalidationsOpReq);
			if (validateResponse.isSuccess()) {
				String transactionRefNum = context.getOrgRefNo();

				setIntoProcessMap(httpRequest, BMGProcessConstants.APPLY_TD, transactionRefNum, validateResponse);

				setIntoProcessMap(httpRequest, BMGProcessConstants.APPLY_TD, ApplyTDConstants.TXN_REF_NO, transactionRefNum);


				validateResponse.setTransactionRefNum(transactionRefNum);
			} else {
				validateResponse.setResMsg("Validation failed");
			}

		}
		/*
		 * applyTDValidationOperationResponse.getContext().setValue(
		 * AccountConstants.CORRELATION_ID, actNo);
		 */

		return (BMBBaseResponseModel) bmbJSONBuilder
				.createJSONResponse(validateResponse);
	}

	@Override
	protected String getActivityId(Object command) {

		return ActivityConstant.OPEN_TERMDEPOSIT_ACTIVITY_ID;
	}

	/**
	 * make TDAccountDetailsOperationRequest
	 *
	 * @param httpRequest
	 * @return
	 */
	private ApplyTDValidationOperationRequest makeRequest(
			HttpServletRequest httpRequest) {
		Context context = createContext(httpRequest);
		ApplyTDValidationOperationRequest request = new ApplyTDValidationOperationRequest();

		context.setActivityId(ActivityConstant.OPEN_TERMDEPOSIT_ACTIVITY_ID);
		request.setContext(context);

		return request;
	}

	public BMBJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public void setApplyTDValidateOperation(
			com.barclays.bmg.dao.operation.ApplyTDValidateOperation applyTDValidateOperation) {
		this.applyTDValidateOperation = applyTDValidateOperation;
	}

	public com.barclays.bmg.dao.operation.ApplyTDValidateOperation getApplyTDValidateOperation() {
		return applyTDValidateOperation;
	}

}
