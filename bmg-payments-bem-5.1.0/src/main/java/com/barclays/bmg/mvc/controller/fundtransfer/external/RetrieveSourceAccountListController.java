package com.barclays.bmg.mvc.controller.fundtransfer.external;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.RetrievePayeeListCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.beneficiary.FilterUrgentPayeeListOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;

public class RetrieveSourceAccountListController extends BMBAbstractCommandController {

	private RetrieveAccountListOperation retrieveAccountListOperation;
	private FilterUrgentPayeeListOperation filterUrgentPayeeListOperation;
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private String activityId;
	private String payGrp;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1 (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse response, Object command, BindException errors)
	throws Exception {

		RetrievePayeeListCommand retrievePayeeListCommand = (RetrievePayeeListCommand) command;
		String serviceName = null;
		if (!StringUtils.isEmpty(retrievePayeeListCommand.getServiceName())) {
			serviceName = retrievePayeeListCommand.getServiceName();
		}
		// Get the payee List.
		setFirstStep(httpRequest);
		Context context = createContext(httpRequest);
		context.setActivityId(activityId);
		// Get the source accounts.

		RetrieveAcctListOperationRequest retrieveAcctListOperationRequest = new RetrieveAcctListOperationRequest();
		retrieveAcctListOperationRequest.setContext(context);

		RetrieveAcctListOperationResponse retrieveAcctListOperationResponse = retrieveAccountListOperation
		.getSourceAccountsForLocalCurrency(retrieveAcctListOperationRequest);

		if (retrieveAcctListOperationResponse.isSuccess()) {
			RetrievePayeeListOperationRequest retrievePayeeListOperationRequest = new RetrievePayeeListOperationRequest();
			retrievePayeeListOperationRequest.setContext(context);
			mapCorrelationIds(retrieveAcctListOperationResponse.getAcctList(), retrievePayeeListOperationRequest,httpRequest,
					retrieveAcctListOperationResponse, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER);
		}
		return (BMBBaseResponseModel) bmbJSONBuilder.createJSONResponse(retrieveAcctListOperationResponse);
	}


	public RetrieveAccountListOperation getRetrieveAccountListOperation() {
		return retrieveAccountListOperation;
	}

	public void setRetrieveAccountListOperation(RetrieveAccountListOperation retrieveAccountListOperation) {
		this.retrieveAccountListOperation = retrieveAccountListOperation;
	}

	public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
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

	public FilterUrgentPayeeListOperation getFilterUrgentPayeeListOperation() {
		return filterUrgentPayeeListOperation;
	}

	public void setFilterUrgentPayeeListOperation(FilterUrgentPayeeListOperation filterUrgentPayeeListOperation) {
		this.filterUrgentPayeeListOperation = filterUrgentPayeeListOperation;
	}

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return null;
	}

}
