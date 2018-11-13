package com.barclays.bmg.ussd.mvc.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.ussd.auth.operation.request.SelfRegistrationInitOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.SelfRegistrationInitOperationResponse;
import com.barclays.bmg.ussd.mvc.command.auth.SelfRegistrationInitCommand;
import com.barclays.bmg.ussd.operation.SelfRegistrationInitOperation;

public class SearchIndividualCustByAcctCtrl extends BMBAbstractCommandController {

    private String activityId;
    private SelfRegistrationInitOperation selfRegistrationInitOperation;
    private BMBJSONBuilder selfRegistrationInitJSONBldr;

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub`
		return null;
	}

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		setFirstStep(request);

		SelfRegistrationInitCommand selfRegistrationInitCommand = (SelfRegistrationInitCommand) command;

		SelfRegistrationInitOperationRequest selfRegistrationInitOperationRequest = makeRequest(request);
		selfRegistrationInitOperationRequest.setMobileNo(selfRegistrationInitCommand.getMobileNo());
		selfRegistrationInitOperationRequest.setAccountNo(selfRegistrationInitCommand.getAccountNo());
		selfRegistrationInitOperationRequest.setBranchCode(selfRegistrationInitCommand.getBranchCode());

		Context context = selfRegistrationInitOperationRequest.getContext();
		context.setMobilePhone(selfRegistrationInitCommand.getMobileNo());
		selfRegistrationInitOperationRequest.setContext(context);

		setIntoProcessMap(request, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, RequestConstants.MOBILE_NO, selfRegistrationInitCommand.getMobileNo());
		setIntoProcessMap(request, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, RequestConstants.ACCOUNT_NO, selfRegistrationInitCommand
			.getAccountNo());
		setIntoProcessMap(request, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, RequestConstants.BRANCH_CODE, selfRegistrationInitCommand
			.getBranchCode());
		setIntoProcessMap(request, BMGProcessConstants.NON_BARCLAYS_FUND_TRANSFER, RequestConstants.SELF_REG_INIT_FLOW_ID,
			BMGProcessConstants.SELF_REG_INIT_FLOW_ID);


		 SelfRegistrationInitOperationResponse selfRegisInitOperationResponse = selfRegistrationInitOperation
			.nameQueryByAccountNoAndBankCode(selfRegistrationInitOperationRequest);

		 /*String scvId=selfRegisInitOperationResponse.getContext().getCustomerId();
		 String bankCif=(String)selfRegisInitOperationResponse.getContext().getContextMap().get(RequestConstants.BANK_CIF);
		 setIntoProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.SCV_ID,scvId);
		 setIntoProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.BANK_CIF,bankCif);*/
		 return (BMBBaseResponseModel) selfRegistrationInitJSONBldr.createJSONResponse(selfRegisInitOperationResponse);

	}
	 /**
     * This method makeRequest has the purpose to construct the operation request.
     *
     * @param HttpServletRequest
     * @return SelfRegistrationInitOperationRequest
     */
    private SelfRegistrationInitOperationRequest makeRequest(HttpServletRequest request) {
	SelfRegistrationInitOperationRequest selfRegisInitOperationRequest = new SelfRegistrationInitOperationRequest();

	Context context = createContext(request);
	context.setActivityId(getActivityId());
	context.setOpCde(String.valueOf(request.getParameter("opCde")));
	selfRegisInitOperationRequest.setContext(context);

	return selfRegisInitOperationRequest;

    }

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public SelfRegistrationInitOperation getSelfRegistrationInitOperation() {
		return selfRegistrationInitOperation;
	}

	public void setSelfRegistrationInitOperation(
			SelfRegistrationInitOperation selfRegistrationInitOperation) {
		this.selfRegistrationInitOperation = selfRegistrationInitOperation;
	}

	public BMBJSONBuilder getSelfRegistrationInitJSONBldr() {
		return selfRegistrationInitJSONBldr;
	}

	public void setSelfRegistrationInitJSONBldr(
			BMBJSONBuilder selfRegistrationInitJSONBldr) {
		this.selfRegistrationInitJSONBldr = selfRegistrationInitJSONBldr;
	}




}
