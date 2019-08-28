package com.barclays.bmg.mvc.controller.addprospect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.SessionActivityOperation;
import com.barclays.bmg.operation.addprospect.AddProspectOperation;
import com.barclays.bmg.operation.request.AddProspectOperationRequest;
import com.barclays.bmg.operation.response.AddProspectOperationResponse;

public class AddProspectController extends BMBAbstractCommandController {

    private AddProspectOperation addProspectOperation;
    private BMBJSONBuilder bmbJsonBuilder;
    private SessionActivityOperation sessionActivityOperation;

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {
	System.out.println("Inside Controller..............");
	Context context = createContext(request);
	AddProspectOperationRequest addProspectOperationRequest = makeRequest(request, context);
	AddProspectOperationResponse opRes = addProspectOperation.addProspect(addProspectOperationRequest);

	System.out.println("   ***  AddProspectOperationResponse REs Code " + opRes.getResCde());
	System.out.println("   ***  AddProspectOperationResponse REs MSG " + opRes.getResMsg());
	System.out.println("   ***  AddProspectOperationResponse REs Assigned to  " + opRes.getAssignedTo());
	System.out.println("   ***  AddProspectOperationResponse REs Prospect ID  " + opRes.getProspectId());
	System.out.println("   ***  AddProspectOperationResponse REs Duplicaet Flag " + opRes.isDuplicateFlag());

	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(opRes);
    }

    /**
     * The method is written for Make request.
     * 
     * @param request
     *            the request
     * @param context
     *            the context
     * @return the RetrieveAllCustAcctOperationRequest's object
     */
    private AddProspectOperationRequest makeRequest(HttpServletRequest request, Context context) {
	AddProspectOperationRequest opReq = new AddProspectOperationRequest();
	context.setActivityId(ActivityConstant.ACT_ADD_PROSPECT);
	opReq.setContext(context);
	return opReq;
    }

    public AddProspectOperation getAddProspectOperation() {
	return addProspectOperation;
    }

    public void setAddProspectOperation(AddProspectOperation addProspectOperation) {
	this.addProspectOperation = addProspectOperation;
    }

    public BMBJSONBuilder getBmbJsonBuilder() {
	return bmbJsonBuilder;
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    public SessionActivityOperation getSessionActivityOperation() {
	return sessionActivityOperation;
    }

    public void setSessionActivityOperation(SessionActivityOperation sessionActivityOperation) {
	this.sessionActivityOperation = sessionActivityOperation;
    }

    @Override
    protected String getActivityId(Object command) {
	return ActivityConstant.ACT_ADD_PROSPECT;
    }

}