package com.barclays.bmg.mvc.controller.accountdetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.accountdetails.CreditCardStmtTransCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountdetails.CreditCardStmtTransOperation;
import com.barclays.bmg.operation.accountdetails.request.CreditCardStmtTransOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.CreditCardStmtTransOperationResponse;

/**
 * @author BMB Team
 *
 */

public class CreditCardStmtTransController extends BMBAbstractCommandController {

    private CreditCardStmtTransOperation creditCardStmtTransOperation;
    private BMBJSONBuilder bmbJsonBuilder;

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {

	CreditCardStmtTransCommand ccStmtTransCommand = (CreditCardStmtTransCommand) command;

	CreditCardStmtTransOperationRequest ccStmtTransOperationReq = makeRequest(httpRequest);
	ccStmtTransOperationReq.setAccountNo(getAccountNumber(ccStmtTransCommand.getActNo(), httpRequest, BMGProcessConstants.CREDIT_PAYMENT));

	ccStmtTransOperationReq.setCardNo(ccStmtTransCommand.getCrdNo());

	CreditCardStmtTransOperationResponse ccStmtTransOperationResp = creditCardStmtTransOperation
		.retrieveCreditCardStmtTrans(ccStmtTransOperationReq);

	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(ccStmtTransOperationResp);

    }

    /**
     * makes CreditCardStmtTransOperationRequest object
     *
     * @param request
     * @return
     */
    private CreditCardStmtTransOperationRequest makeRequest(HttpServletRequest request) {

	CreditCardStmtTransOperationRequest ccStmtOperationRequest = new CreditCardStmtTransOperationRequest();

	Context context = createContext(request);

	context.setActivityId(ActivityConstant.CREDIT_CARD_STATEMENT_TRANS_ACTIVITY_ID);

	ccStmtOperationRequest.setContext(context);

	return ccStmtOperationRequest;

    }

    public void setCreditCardStmtTransOperation(CreditCardStmtTransOperation creditCardStmtTransOperation) {
	this.creditCardStmtTransOperation = creditCardStmtTransOperation;
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    @Override
    protected String getActivityId(Object command) {
	return ActivityConstant.CREDIT_CARD_STATEMENT_TRANS_ACTIVITY_ID;
    }

}
