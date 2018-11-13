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
import com.barclays.bmg.operation.accountdetails.CASADetailsOperation;
import com.barclays.bmg.operation.accountdetails.request.CASADetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.CASADetailsOperationResponse;

public class CasaAccountDetailsController extends BMBAbstractCommandController {

    private CASADetailsOperation casaDetailsOperation;
    private BMBJSONBuilder bmbJsonBuilder;

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {
	setLastStep(httpRequest);
	AccountDetailsCommand actDtlsCommand = (AccountDetailsCommand) command;

	CASADetailsOperationRequest casaDetailsOperationRequest = makeRequest(httpRequest);

	String actNoCorr = actDtlsCommand.getActNo();
	casaDetailsOperationRequest.setAccountNo(getAccountNumber(actNoCorr, httpRequest, BMGProcessConstants.ACCOUNTS_PROCESS));

	CASADetailsOperationResponse casaDetailsOperationResponse = casaDetailsOperation.retrieveCASADetails(casaDetailsOperationRequest);

	if (casaDetailsOperationResponse != null && casaDetailsOperationResponse.isSuccess()) {
	    casaDetailsOperationResponse.getContext().setValue(AccountConstants.CORRELATION_ID, actNoCorr);
	}

	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(casaDetailsOperationResponse);
    }

    private CASADetailsOperationRequest makeRequest(HttpServletRequest request) {

	CASADetailsOperationRequest casaDetailsOperationRequest = new CASADetailsOperationRequest();

	Context context = createContext(request);

	context.setActivityId(ActivityConstant.CASA_DETAIL_ACTIVITY_ID);

	casaDetailsOperationRequest.setContext(context);

	return casaDetailsOperationRequest;

    }

    public void setCasaDetailsOperation(CASADetailsOperation casaDetailsOperation) {
	this.casaDetailsOperation = casaDetailsOperation;
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    @Override
    protected String getActivityId(Object command) {

	return ActivityConstant.CASA_DETAIL_ACTIVITY_ID;
    }

}
