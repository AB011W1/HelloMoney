package com.barclays.bmg.mvc.controller.billpayment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.accountdetails.CreditCardValidationCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.beneficiary.CreditCardValidationOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.CreditCardValidationOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.CreditCardValidationOperationResponse;

public class CreditCardValidationController extends BMBAbstractCommandController {

    private CreditCardValidationOperation creditCardValidationOperation;
    private BMBJSONBuilder bmbJsonBuilder;

    @Override
    protected String getActivityId(Object command) {
	return ActivityIdConstantBean.BARCLAY_CARD_PAYMENT_ACTIVITY_ID;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {

	Context context = createContext(httpRequest);
	context.setActivityId(getActivityId(command));
	clearCorrelationIds(httpRequest, BMGProcessConstants.BILL_PAYMENT);
	CreditCardValidationCommand creditCardValidationCommand = (CreditCardValidationCommand) command;

	CreditCardValidationOperationRequest creditCardOperationRequest = new CreditCardValidationOperationRequest();
	creditCardOperationRequest.setContext(context);

	creditCardOperationRequest.setCrdNo(creditCardValidationCommand.getCardNo());

	CreditCardValidationOperationResponse creditCardValidationOperationResponse = creditCardValidationOperation
		.validateCreditCard(creditCardOperationRequest);

	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(creditCardValidationOperationResponse);
    }

    public void setCreditCardValidationOperation(CreditCardValidationOperation creditCardValidationOperation) {
	this.creditCardValidationOperation = creditCardValidationOperation;
    }

    public BMBJSONBuilder getBmbJsonBuilder() {
	return bmbJsonBuilder;
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    public CreditCardValidationOperation getCreditCardValidationOperation() {
	return creditCardValidationOperation;
    }

}
