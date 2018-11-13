package com.barclays.bmg.mvc.controller.fundtransfer.external;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.RetrievePayeeListCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.beneficiary.FilterUrgentPayeeListOperation;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeListOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.FilterUrgentPayeeListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.FilterUrgentPayeeListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeListOperationResponse;

public class RetrievePayeeListController extends BMBAbstractCommandController {

    private RetrievePayeeListOperation retrievePayeeListOperation;
    private RetrieveAccountListOperation retrieveAccountListOperation;
    private FilterUrgentPayeeListOperation filterUrgentPayeeListOperation;
    private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
    private String urgent;
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
	RetrievePayeeListOperationRequest retrievePayeeListOperationRequest = new RetrievePayeeListOperationRequest();
	retrievePayeeListOperationRequest.setContext(context);
	retrievePayeeListOperationRequest.setPayGrp(payGrp);

	RetrievePayeeListOperationResponse retrievePayeeListOperationResponse = retrievePayeeListOperation
		.retrievePayeeList(retrievePayeeListOperationRequest);
	// Get the source accounts.

	FilterUrgentPayeeListOperationResponse filterUrgentPayeeListOperationResponse = null;
	// Filter the urgent payee.
	if (retrievePayeeListOperationResponse.isSuccess()) {
	    FilterUrgentPayeeListOperationRequest filterUrgentPayeeListOperationRequest = new FilterUrgentPayeeListOperationRequest();
	    filterUrgentPayeeListOperationRequest.setContext(context);
	    filterUrgentPayeeListOperationRequest.setCategorizedPayeeList(retrievePayeeListOperationResponse.getCategorizedPayeeList());
	    filterUrgentPayeeListOperationRequest.setUrgent(getBoolean(urgent));
	    filterUrgentPayeeListOperationResponse = filterUrgentPayeeListOperation.filterUrgentPayees(filterUrgentPayeeListOperationRequest);
	    retrievePayeeListOperationResponse.setCategorizedPayeeList(filterUrgentPayeeListOperationResponse.getCategorizedPayeeList());
	}

	if (RequestConstants.SERVICE_NAME_FUND_TRANSFEER.equalsIgnoreCase(serviceName)) {
	    RetrieveAcctListOperationRequest retrieveAcctListOperationRequest = new RetrieveAcctListOperationRequest();
	    retrieveAcctListOperationRequest.setContext(context);

	    RetrieveAcctListOperationResponse retrieveAcctListOperationResponse = retrieveAccountListOperation
		    .getSourceAccountsForLocalCurrency(retrieveAcctListOperationRequest);

	    if (retrieveAcctListOperationResponse.isSuccess()) {
		mapCorrelationIds(retrieveAcctListOperationResponse.getAcctList(), retrievePayeeListOperationRequest, httpRequest,
			retrieveAcctListOperationResponse, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER);
		retrieveAcctListOperationResponse.setContext(retrievePayeeListOperationRequest.getContext());
	    }
	    return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(retrievePayeeListOperationResponse,
		    retrieveAcctListOperationResponse, filterUrgentPayeeListOperationResponse);
	} else {
	    return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(retrievePayeeListOperationResponse,
		    new RetrieveAcctListOperationResponse(), filterUrgentPayeeListOperationResponse);
	}
    }

    private boolean getBoolean(String value) {
	String TRUE = "true";
	if (TRUE.equals(value)) {
	    return true;
	}
	return false;
    }

    public RetrievePayeeListOperation getRetrievePayeeListOperation() {
	return retrievePayeeListOperation;
    }

    public void setRetrievePayeeListOperation(RetrievePayeeListOperation retrievePayeeListOperation) {
	this.retrievePayeeListOperation = retrievePayeeListOperation;
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

    public String getUrgent() {
	return urgent;
    }

    public void setUrgent(String urgent) {
	this.urgent = urgent;
    }

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

}
