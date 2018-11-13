package com.barclays.bmg.mvc.controller.lookup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.lookup.BranchLookUpCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.mvc.operation.lookup.BranchLookUpOperation;
import com.barclays.bmg.operation.request.lookup.BranchLookUpOperationRequest;
import com.barclays.bmg.operation.response.lookup.BranchLookUpOperationResponse;

public class BranchLookUpController extends BMBAbstractCommandController {

	private BranchLookUpOperation branchLookUpOperation;

	private BMBJSONBuilder bmbBuilder;

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return "PMT_FT_BANK_LOOKUP";
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		Context context = createContext(request);
		context.setActivityId(getActivityId(null));

		BranchLookUpCommand branchCommand = (BranchLookUpCommand) command;

		BranchLookUpOperationRequest branchLookUpOperationRequest = new BranchLookUpOperationRequest();
		branchLookUpOperationRequest.setContext(context);
		branchLookUpOperationRequest.setBranchName(branchCommand.getBrnNam());
		branchLookUpOperationRequest.setCityName(branchCommand.getCty());

		BranchLookUpOperationResponse branchLookUpOperationResponse = branchLookUpOperation
				.retrieveBranches(branchLookUpOperationRequest);

		return (BMBBaseResponseModel) bmbBuilder
				.createJSONResponse(branchLookUpOperationResponse);
	}

	public BranchLookUpOperation getBranchLookUpOperation() {
		return branchLookUpOperation;
	}

	public void setBranchLookUpOperation(
			BranchLookUpOperation branchLookUpOperation) {
		this.branchLookUpOperation = branchLookUpOperation;
	}

	public BMBJSONBuilder getBmbBuilder() {
		return bmbBuilder;
	}

	public void setBmbBuilder(BMBJSONBuilder bmbBuilder) {
		this.bmbBuilder = bmbBuilder;
	}

}
