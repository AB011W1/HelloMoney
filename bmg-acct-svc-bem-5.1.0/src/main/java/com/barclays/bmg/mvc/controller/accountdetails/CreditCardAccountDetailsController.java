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
import com.barclays.bmg.mvc.command.accountdetails.CreditCardAccountDetailsCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountdetails.CreditCardDetailsOperation;
import com.barclays.bmg.operation.accountdetails.request.CreditCardDetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.CreditCardDetailsOperationResponse;

public class CreditCardAccountDetailsController extends BMBAbstractCommandController {

    private CreditCardDetailsOperation creditCardDetailsOperation;
    private BMBJSONBuilder bmbJsonBuilder;

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1 (javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
     *
     * 1. Retrieve creditcard account details by sending creditcard account number. 2. sends CreditCardDetailsOperationResponse to JSON builder
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {

	CreditCardAccountDetailsCommand ccActDetlCommand = (CreditCardAccountDetailsCommand) command;

	CreditCardDetailsOperationRequest creditCardDetailsOperationRequest = makeRequest(httpRequest);

	String corrActNo = ccActDetlCommand.getActNo();
	creditCardDetailsOperationRequest.setAccountNo(getAccountNumber(corrActNo, httpRequest, BMGProcessConstants.CREDIT_PAYMENT));

	CreditCardDetailsOperationResponse ccDetailsOperationResponse = creditCardDetailsOperation
		.retrieveCreditCardDetails(creditCardDetailsOperationRequest);

	if (ccDetailsOperationResponse != null && ccDetailsOperationResponse.isSuccess()) {

	    ccDetailsOperationResponse.getContext().setValue(AccountConstants.CORRELATION_ID, corrActNo);
	}

	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(ccDetailsOperationResponse);

    }

    /**
     * makes CreditCardDetailsOperationRequest object
     *
     * @param request
     * @return
     */
    private CreditCardDetailsOperationRequest makeRequest(HttpServletRequest request) {

	CreditCardDetailsOperationRequest casaDetailsOperationRequest = new CreditCardDetailsOperationRequest();

	Context context = createContext(request);

	context.setActivityId(ActivityConstant.CREDIT_DETAIL_ACTIVITY_ID);

	casaDetailsOperationRequest.setContext(context);

	return casaDetailsOperationRequest;

    }

    public void setCreditCardDetailsOperation(CreditCardDetailsOperation creditCardDetailsOperation) {
	this.creditCardDetailsOperation = creditCardDetailsOperation;
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    @Override
    protected String getActivityId(Object command) {
	return ActivityConstant.CREDIT_DETAIL_ACTIVITY_ID;
    }
}
