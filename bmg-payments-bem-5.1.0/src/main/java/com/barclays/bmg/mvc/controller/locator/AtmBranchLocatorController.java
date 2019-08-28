package com.barclays.bmg.mvc.controller.locator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.locator.AtmBranchCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.mvc.operation.locator.AtmBranchLocatorOperation;
import com.barclays.bmg.mvc.operation.locator.request.AtmBranchLocatorOperationRequest;
import com.barclays.bmg.mvc.operation.locator.response.AtmBranchLocatorOperationResponse;

public class AtmBranchLocatorController extends BMBAbstractCommandController {

    private AtmBranchLocatorOperation atmBranchLocatorOperation;
    private BMBJSONBuilder bmbBuilder;

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	setFirstStep(httpRequest);

	clearCorrelationIds(httpRequest, BMGProcessConstants.ATM_BRANCH_LOCATOR);

	Context context = buildContext(httpRequest);

	AtmBranchLocatorOperationRequest atmBranchLocatorOperationRequest = new AtmBranchLocatorOperationRequest();
	atmBranchLocatorOperationRequest.setContext(context);

	AtmBranchLocatorOperationResponse atmBranchLocatorOperationResponse = null;
	AtmBranchCommand atmBranchCommand = (AtmBranchCommand) command;

	atmBranchLocatorOperationRequest.setArea(atmBranchCommand.getArea());
	atmBranchLocatorOperationRequest.setCity(atmBranchCommand.getCity());
	atmBranchLocatorOperationRequest.setBusinessId(atmBranchCommand.getBusinessId());
	atmBranchLocatorOperationRequest.setActivityId(atmBranchCommand.getActivityId());

	if (atmBranchCommand.getAuditRequired().equalsIgnoreCase("Y")) {

	    String activityid = atmBranchCommand.getActivityId();

	    if (activityid.equalsIgnoreCase("BranchLocator")) {

		atmBranchLocatorOperationResponse = atmBranchLocatorOperation.retrieveBranchList(atmBranchLocatorOperationRequest);
	    }

	    if (activityid.equalsIgnoreCase("ATMLocator")) {
		atmBranchLocatorOperationResponse = atmBranchLocatorOperation.retrieveATMList(atmBranchLocatorOperationRequest);

	    }

	} else {
	    atmBranchLocatorOperationResponse = atmBranchLocatorOperation.retrieveAtmBranchList(atmBranchLocatorOperationRequest);
	}

	return (BMBBaseResponseModel) bmbBuilder.createJSONResponse(atmBranchLocatorOperationResponse);

    }

    protected Context buildContext(HttpServletRequest httpRequest) {
	Context context = createContext(httpRequest);
	context.setActivityId("BranchLocator");
	return context;

    }

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    public AtmBranchLocatorOperation getAtmBranchLocatorOperation() {
	return atmBranchLocatorOperation;
    }

    public void setAtmBranchLocatorOperation(AtmBranchLocatorOperation atmBranchLocatorOperation) {
	this.atmBranchLocatorOperation = atmBranchLocatorOperation;
    }

    public BMBJSONBuilder getBmbBuilder() {
	return bmbBuilder;
    }

    public void setBmbBuilder(BMBJSONBuilder bmbBuilder) {
	this.bmbBuilder = bmbBuilder;
    }

}
