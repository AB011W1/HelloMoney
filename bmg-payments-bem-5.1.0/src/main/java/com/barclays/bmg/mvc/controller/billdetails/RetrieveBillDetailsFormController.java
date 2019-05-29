package com.barclays.bmg.mvc.controller.billdetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.RetrieveBillDetailsCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountservices.RetrieveBillDetailsOperation;
import com.barclays.bmg.service.request.RetrieveBillDetailsServiceRequest;
import com.barclays.bmg.service.response.RetrieveBillDetailsServiceResponse;

public class RetrieveBillDetailsFormController extends BMBAbstractCommandController {

	private static final Logger LOGGER = Logger.getLogger(RetrieveBillDetailsFormController.class);

    private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
    private RetrieveBillDetailsOperation retrieveBillDetailsOperation;
    private String activityId;
    private String txnType;

	@Override
    protected String getActivityId(Object command) {
    	return activityId; // PMT_BP_BILLPAY_ONETIME;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {

    	RetrieveBillDetailsCommand billDetailsCommand = (RetrieveBillDetailsCommand) command;
    	Context context = createContext(httpRequest);
    	LOGGER.debug("RetrieveBillDetails Request : " + billDetailsCommand.toString());

    	RetrieveBillDetailsServiceRequest billDetailsOperationRequest = new RetrieveBillDetailsServiceRequest();
    	billDetailsOperationRequest.setBillerID(billDetailsCommand.getBillerID());
    	billDetailsOperationRequest.setBillerName(billDetailsCommand.getBillerName());
    	billDetailsOperationRequest.setControlNumber(billDetailsCommand.getControlNumber());
    	billDetailsOperationRequest.setContext(context);

    	RetrieveBillDetailsServiceResponse billDetailsOperationResponse = retrieveBillDetailsOperation.getBillDetails(billDetailsOperationRequest);

		return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(
				billDetailsOperationResponse);
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

    public String getTxnType() {
    	return txnType;
    }

    public void setTxnType(String txnType) {
    	this.txnType = txnType;
    }

    /**
     * @return the retrieveBillDetailsOperation
     */
    public RetrieveBillDetailsOperation getRetrieveBillDetailsOperation() {
    	return retrieveBillDetailsOperation;
    }

    /**
     * @param retrieveBillDetailsOperation the retrieveBillDetailsOperation to set
     */
    public void setRetrieveBillDetailsOperation(RetrieveBillDetailsOperation retrieveBillDetailsOperation) {
    	this.retrieveBillDetailsOperation = retrieveBillDetailsOperation;
    }
}