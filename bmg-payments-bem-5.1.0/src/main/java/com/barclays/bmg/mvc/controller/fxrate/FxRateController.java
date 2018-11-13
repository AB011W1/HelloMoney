package com.barclays.bmg.mvc.controller.fxrate;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.fxrate.mvc.command.FxRateCommand;
import com.barclays.bmg.fxrate.operation.request.FxRateOperationRequest;
import com.barclays.bmg.fxrate.operation.response.FxRateOperationResponse;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.fxrate.FxRateOperation;

/**
 * ******************************************************
 * 
 * @author e20042299 </br> * The Class FxRateController
 */
public class FxRateController extends BMBAbstractCommandController {

    private String activityId;

    private FxRateOperation fxRateOperation;

    private BMBJSONBuilder bmbJSONBuilder;

    public FxRateOperation getFxRateOperation() {
	return fxRateOperation;
    }

    public void setFxRateOperation(FxRateOperation fxRateOperation) {
	this.fxRateOperation = fxRateOperation;
    }

    public BMBJSONBuilder getBmbJSONBuilder() {
	return bmbJSONBuilder;
    }

    public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
	this.bmbJSONBuilder = bmbJSONBuilder;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	setFirstStep(request);

	FxRateCommand fxRateCommand = (FxRateCommand) command;

	Context context = buildContext(request);

	String amt = "1";
	// Having this if clause because in case of view fx rate it will be null. In other cases it may not be null.
	if (fxRateCommand.getAmt() != null) {
	    amt = fxRateCommand.getAmt();
	}
	String srcCurrency = fxRateCommand.getSrcCurrency();
	String destCurrency = fxRateCommand.getDestCurrency();
	String actNo = getAccountNumber(fxRateCommand.getActNo(), request, BMGProcessConstants.ACCOUNTS_PROCESS);

	String branchCode = fxRateCommand.getBranchCode();
	String tranType = fxRateCommand.getTranType();

	/*
	 * String amt = "40001"; String srcCurrency = "KES"; String destCurrency = "USD";
	 */
	FxRateOperationRequest fxRateOperationRequest = new FxRateOperationRequest();

	fxRateOperationRequest.setTxnAmt(new BigDecimal(amt));
	fxRateOperationRequest.setContext(context);
	fxRateOperationRequest.setSrcCurrency(srcCurrency);
	fxRateOperationRequest.setDestCurrency(destCurrency);
	fxRateOperationRequest.setActNo(actNo);
	fxRateOperationRequest.setBranchCode(branchCode);
	fxRateOperationRequest.setTxnType(tranType);

	FxRateOperationResponse fxRateOperationResponse = null;

	fxRateOperationResponse = fxRateOperation.retrieveFxRate(fxRateOperationRequest);

	return (BMBBaseResponseModel) ((BMBMultipleResponseJSONBuilder) bmbJSONBuilder).createMultipleJSONResponse(fxRateOperationResponse);

    }

    @Override
    protected String getAccountNumber(String correlationId, HttpServletRequest httpRequest, String processKey) {
	Map<String, String> accountMap = (Map<String, String>) getFromProcessMap(httpRequest, processKey, AccountConstants.ACCOUNT_MAP);

	String acct = null;
	if (accountMap != null) {
	    acct = accountMap.get(correlationId);
	}
	return acct;
    }

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return "PMT_FX_RATE";
    }

    protected Context buildContext(HttpServletRequest httpRequest) {

	Context context = createContext(httpRequest);

	Map<String, Object> userMap = getUserMapFromSession(httpRequest);
	context.setPpMap((Map<String, String>) userMap.get(SessionConstant.SESSION_PP_MAP));

	context.setActivityId("PMT_FX_RATE");
	return context;

    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public String getActivityId() {
	return activityId;
    }
}
