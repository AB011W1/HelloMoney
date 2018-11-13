package com.barclays.bmg.mvc.controller.billpayment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.operation.accounts.AccountSummaryOperation;
import com.barclays.bmg.operation.accounts.request.AccountSummaryOperationRequest;
import com.barclays.bmg.operation.accounts.response.AccountSummaryOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class RetrieveCCDAccountController extends BMBAbstractController {

    private AccountSummaryOperation accountSummaryOperation;

    private BMBJSONBuilder bmbJsonBuilder;

    /*
     * Retrieve all legible account List
     */
    @SuppressWarnings("deprecation")
    @Override
    protected BMBBaseResponseModel handleRequestInternal1(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
	// TODO Auto-generated method stub

	AccountSummaryOperationRequest accountSummaryOperationRequest = makeRequest(httpRequest);
	clearCorrelationIds(httpRequest, BMGProcessConstants.CREDIT_CARD_PAYMENT);
	AccountSummaryOperationResponse accountSummaryOperationResponse = accountSummaryOperation.retrieveAllAccount(accountSummaryOperationRequest);

	if (accountSummaryOperationResponse.isSuccess()) {
	    mapCorrelationIds(accountSummaryOperationResponse.getAccountList(), accountSummaryOperationRequest, httpRequest,
		    accountSummaryOperationResponse, BMGProcessConstants.CREDIT_CARD_PAYMENT);

	}
	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(accountSummaryOperationResponse);

    }

    private AccountSummaryOperationRequest makeRequest(HttpServletRequest request) {

	AccountSummaryOperationRequest accountSummaryOperationRequest = new AccountSummaryOperationRequest();

	Context context = createContext(request);

	context.setActivityId(ActivityConstant.ACCOUNT_SUMMARY_ACTIVITY_ID);

	context.setActivityRefNo(BMBCommonUtility.generateRefNo());

	accountSummaryOperationRequest.setContext(context);

	return accountSummaryOperationRequest;

    }

    public void setAccountSummaryOperation(AccountSummaryOperation accountSummaryOperation) {
	this.accountSummaryOperation = accountSummaryOperation;
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    @Override
    protected String getActivityId() {

	return ActivityConstant.ACCOUNT_SUMMARY_ACTIVITY_ID;
    }

}
