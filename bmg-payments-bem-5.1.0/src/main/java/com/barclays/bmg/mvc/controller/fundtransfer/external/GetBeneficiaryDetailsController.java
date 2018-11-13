package com.barclays.bmg.mvc.controller.fundtransfer.external;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.ExternalPayeeDetailsCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeInfoOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;

public class GetBeneficiaryDetailsController  extends BMBAbstractCommandController  {

	private RetrievePayeeInfoOperation retrievePayeeInfoOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private String activityId;
	private String payGrp;
	@Override
	protected String getActivityId(Object command) {
		return activityId;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Object command, BindException errors)
			throws Exception {

		ExternalPayeeDetailsCommand externalPayeeDetailsCommand = (ExternalPayeeDetailsCommand)command;
		RetrievePayeeInfoOperationRequest retrievePayeeInfoOperationRequest = new RetrievePayeeInfoOperationRequest();
		addContext(retrievePayeeInfoOperationRequest, httpRequest);
		retrievePayeeInfoOperationRequest.setPayId(externalPayeeDetailsCommand.getPayId());
		retrievePayeeInfoOperationRequest.setPayGrp(payGrp);
		RetrievePayeeInfoOperationResponse retrievePayeeInfoOperationResponse = retrievePayeeInfoOperation.retrievePayeeInfo(retrievePayeeInfoOperationRequest);

		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(retrievePayeeInfoOperationResponse);
	}

	/**
	 * @param request
	 * @param httpRequest
	 *  add required parameters to context from session Map.
	 */
	private void addContext(RequestContext request,
			HttpServletRequest httpRequest) {
		Context context = createContext(httpRequest);

		context.setActivityId(activityId);
		request.setContext(context);
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

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getPayGrp() {
		return payGrp;
	}

	public void setPayGrp(String payGrp) {
		this.payGrp = payGrp;
	}


}
