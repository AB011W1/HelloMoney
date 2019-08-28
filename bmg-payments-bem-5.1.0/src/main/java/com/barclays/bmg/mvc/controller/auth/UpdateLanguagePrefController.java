package com.barclays.bmg.mvc.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.auth.UpdateLanguagePrefCommand;
import com.barclays.bmg.operation.UpdateLanguagePrefOperation;
import com.barclays.bmg.operation.request.UpdateLanguagePrefOperationRequest;
import com.barclays.bmg.operation.response.UpdateLanguagePrefOperationResponse;

public class UpdateLanguagePrefController extends BMBAbstractCommandController {

    private BMBJSONBuilder bmbJsonBuilder;
    private UpdateLanguagePrefOperation updateLanguagePrefOperation;
    private String activityId;

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    public void setUpdateLanguagePrefOperation(UpdateLanguagePrefOperation updateLanguagePrefOperation) {
	this.updateLanguagePrefOperation = updateLanguagePrefOperation;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {
	setLastStep(request);
	UpdateLanguagePrefCommand updateLanguagePrefCommand = (UpdateLanguagePrefCommand) command;
	Context context = createContext(request);
	context.setActivityId(this.activityId);

	UpdateLanguagePrefOperationRequest updateLanguagePrefOperationRequest = new UpdateLanguagePrefOperationRequest();
	updateLanguagePrefOperationRequest.setContext(context);
	updateLanguagePrefOperationRequest.setPrefLang(updateLanguagePrefCommand.getPrefLang());
	UpdateLanguagePrefOperationResponse updateLanguagePrefOperationResponse = updateLanguagePrefOperation
		.updateLanguagePref(updateLanguagePrefOperationRequest);
	updateLanguagePrefOperationResponse.setContext(context);

	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(updateLanguagePrefOperationResponse);

    }

}
