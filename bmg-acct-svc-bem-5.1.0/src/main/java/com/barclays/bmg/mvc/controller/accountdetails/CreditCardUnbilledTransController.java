package com.barclays.bmg.mvc.controller.accountdetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.accountdetails.CreditCardUnbilledTransCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountdetails.CreditCardUnbilledTransOperation;
import com.barclays.bmg.operation.accountdetails.request.CreditCardUnbilledTransOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.CreditCardUnbilledTransOperationResponse;

public class CreditCardUnbilledTransController extends BMBAbstractCommandController {

    private CreditCardUnbilledTransOperation creditCardUnbilledTransOperation;
    private BMBJSONBuilder bmbJsonBuilder;

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
     *
     * 1. retrieve the creditcard unbilled transactions 2. create JSON model for CreditCardUnbilledTransOperationResponse
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {

	CreditCardUnbilledTransCommand ccUnbilledTransCommand = (CreditCardUnbilledTransCommand) command;

	CreditCardUnbilledTransOperationRequest ccUnbilledTransOperationReq = makeRequest(httpRequest);

	ccUnbilledTransOperationReq.setAccountNo(getAccountNumber(ccUnbilledTransCommand.getActNo(), httpRequest,
		BMGProcessConstants.CREDIT_PAYMENT));
	ccUnbilledTransOperationReq.setCardNo(ccUnbilledTransCommand.getCrdNo());

	CreditCardUnbilledTransOperationResponse ccUnbilledTransOperationResp = creditCardUnbilledTransOperation
		.retrieveCreditCardUnbilledTrans(ccUnbilledTransOperationReq);

	/*
	 * ccUnbilledTransOperationReq.setAccountNo("123456122578"); ccUnbilledTransOperationReq.setCardNo("4263991234568442");
	 */
	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(ccUnbilledTransOperationResp);

    }

    /**
     * make CreditCardUnbilledTransOperationRequest
     *
     * @param request
     * @return
     */
    private CreditCardUnbilledTransOperationRequest makeRequest(HttpServletRequest request) {

	CreditCardUnbilledTransOperationRequest casaDetailsOperationRequest = new CreditCardUnbilledTransOperationRequest();

	Context context = createContext(request);

	context.setActivityId(ActivityConstant.CREDIT_CARD_UNBILLED_TRANS_ACTIVITY_ID);

	casaDetailsOperationRequest.setContext(context);

	return casaDetailsOperationRequest;

    }

    public void setCreditCardUnbilledTransOperation(CreditCardUnbilledTransOperation creditCardUnbilledTransOperation) {
	this.creditCardUnbilledTransOperation = creditCardUnbilledTransOperation;
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    @Override
    protected String getActivityId(Object command) {
	return ActivityConstant.CREDIT_CARD_UNBILLED_TRANS_ACTIVITY_ID;
    }

}