package com.barclays.bmg.mvc.controller.fundtransfer.internal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.fundtransfer.internal.InternalFTDetailsCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeInfoOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;

public class InternalFundTransferDetailsController extends BMBAbstractCommandController {

	private RetrievePayeeInfoOperation retrievePayeeInfoOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	@Override
	protected String getActivityId(Object command) {
		return ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command, BindException errors)
			throws Exception {
		Context context = createContext(httpRequest);
		context.setActivityId(ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID);

		InternalFTDetailsCommand interFundTransferDetailsCommand = (InternalFTDetailsCommand)command;

		// Retrieve Payee info.
		RetrievePayeeInfoOperationRequest retrievePayeeInfoOperationRequest = new RetrievePayeeInfoOperationRequest();
		retrievePayeeInfoOperationRequest.setContext(context);
		retrievePayeeInfoOperationRequest.setPayId(interFundTransferDetailsCommand.getPayId());
		retrievePayeeInfoOperationRequest.setPayGrp(FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL);
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = retrievePayeeInfoOperation.retrievePayeeInfo(retrievePayeeInfoOperationRequest);

		return (BMBPayload) bmbJSONBuilder.createMultipleJSONResponse(retrievePayeeInfoOperationResponse);
	}

	public RetrievePayeeInfoOperation getRetrievePayeeInfoOperation() {
		return retrievePayeeInfoOperation;
	}

	public void setRetrievePayeeInfoOperation(
			RetrievePayeeInfoOperation retrievePayeeInfoOperation) {
		this.retrievePayeeInfoOperation = retrievePayeeInfoOperation;
	}

	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

}
