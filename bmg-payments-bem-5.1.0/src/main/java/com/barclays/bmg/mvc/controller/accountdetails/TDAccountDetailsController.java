package com.barclays.bmg.mvc.controller.accountdetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.accountdetails.AccountDetailsCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountdetails.TDAccountDetailsOperation;
import com.barclays.bmg.operation.accountdetails.request.TDAccountDetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.TDAccountDetailsOperationResponse;

public class TDAccountDetailsController extends BMBAbstractCommandController {

    private TDAccountDetailsOperation tdAccountDetailsOperation;
    private BMBJSONBuilder bmbJSONBuilder;

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
     * 
     * 1. Retrieve TD Account Details 2. make JSON response
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {

	AccountDetailsCommand acctDtlsCommand = (AccountDetailsCommand) command;
	TDAccountDetailsOperationRequest tdDetailsOperationRequest = makeRequest(httpRequest);
	// Take care of acct No validation in Command Validator.
	String actNoCorr = acctDtlsCommand.getActNo();
	tdDetailsOperationRequest.setAcctNo(getAccountNumber(actNoCorr, httpRequest, BMGProcessConstants.ACCOUNTS_PROCESS));
	TDAccountDetailsOperationResponse tdDetailsOperationResponse = tdAccountDetailsOperation.retreiveTDAccountDetails(tdDetailsOperationRequest);

	tdDetailsOperationResponse.getContext().setValue(AccountConstants.CORRELATION_ID, actNoCorr);

	return (BMBBaseResponseModel) bmbJSONBuilder.createJSONResponse(tdDetailsOperationResponse);
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
    private TDAccountDetailsOperationRequest makeRequest(HttpServletRequest httpRequest) {
	Context context = createContext(httpRequest);
	TDAccountDetailsOperationRequest request = new TDAccountDetailsOperationRequest();

	context.setActivityId(ActivityConstant.TD_ACCOUNT_DETAILS);
	request.setContext(context);

	return request;
    }

    public TDAccountDetailsOperation getTdAccountDetailsOperation() {
	return tdAccountDetailsOperation;
    }

    public void setTdAccountDetailsOperation(TDAccountDetailsOperation tdAccountDetailsOperation) {
	this.tdAccountDetailsOperation = tdAccountDetailsOperation;
    }

    public BMBJSONBuilder getBmbJSONBuilder() {
	return bmbJSONBuilder;
    }

    public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
	this.bmbJSONBuilder = bmbJSONBuilder;
    }

}
