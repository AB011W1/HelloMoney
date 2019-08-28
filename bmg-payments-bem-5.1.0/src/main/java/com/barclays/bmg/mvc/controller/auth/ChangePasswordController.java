package com.barclays.bmg.mvc.controller.auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ChangePasswordResponseCodeConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.auth.ChangePasswordCommand;
import com.barclays.bmg.operation.ChangePasswordOperation;
import com.barclays.bmg.operation.accountdetails.RetrieveAllCustAcctOperation;
import com.barclays.bmg.operation.request.ChangePasswordOperationRequest;
import com.barclays.bmg.operation.request.RetrieveAllCustAcctOperationRequest;
import com.barclays.bmg.operation.response.ChangePasswordOperationResponse;
import com.barclays.bmg.operation.response.RetrieveAllCustAcctOperationResponse;
import com.barclays.bmg.ussd.auth.operation.request.UpdateDetailstoMCEOpRequest;
import com.barclays.bmg.ussd.operation.UpdateDetailstoMCEOperation;

public class ChangePasswordController extends BMBAbstractCommandController {

    private BMBJSONBuilder bmbJsonBuilder;
    private String activityId;
    private ChangePasswordOperation changePasswordOperation;
    private RetrieveAllCustAcctOperation retrieveAllCustAcctOperation;
    private UpdateDetailstoMCEOperation updateDetailstoMCEOperation;
    private static final String USER_MIGRATED = "MIGRATED";
    private static final String USER_FORGOT_PIN = "FORGOTPIN";

    private static final Logger LOGGER = Logger.getLogger(ChangePasswordController.class);

    public UpdateDetailstoMCEOperation getUpdateDetailstoMCEOperation() {
	return updateDetailstoMCEOperation;
    }

    public void setUpdateDetailstoMCEOperation(UpdateDetailstoMCEOperation updateDetailstoMCEOperation) {
	this.updateDetailstoMCEOperation = updateDetailstoMCEOperation;
    }

    public RetrieveAllCustAcctOperation getRetrieveAllCustAcctOperation() {
	return retrieveAllCustAcctOperation;
    }

    public void setRetrieveAllCustAcctOperation(RetrieveAllCustAcctOperation retrieveAllCustAcctOperation) {
	this.retrieveAllCustAcctOperation = retrieveAllCustAcctOperation;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {
    	//Forgot Pin Chnage

    	String forgotPinFlag="";
    	forgotPinFlag=(String)request.getParameter("forgotPinFlag");




	ChangePasswordCommand cpCommand = (ChangePasswordCommand) command;
	ChangePasswordOperationRequest opRequest = new ChangePasswordOperationRequest();
	Context context = createContext(request);
	context.setActivityId(this.activityId);
	opRequest.setContext(context);
	opRequest.setNewPassword(cpCommand.getNewPass());
	//Forgot Pin Change
	if (!(USER_MIGRATED.equalsIgnoreCase(cpCommand.getUsrStatInMCE()) || USER_FORGOT_PIN.equalsIgnoreCase(forgotPinFlag))) {

	    opRequest.setOldPassword(cpCommand.getOldPass());
	}
	opRequest.setAuthType("SP");
	context.setMobilePhone(cpCommand.getMobileNo());

	ChangePasswordOperationResponse opResponse = null;
	if (!cpCommand.getConfNewPass().equals(cpCommand.getNewPass())) {
	    opResponse = new ChangePasswordOperationResponse();
	    opResponse.setSuccess(false);
	    opResponse.setResCde(ChangePasswordResponseCodeConstants.OP_FAILED_RES_CODE);
	    opResponse.setResMsg(ChangePasswordResponseCodeConstants.PASSWORDS_DONT_MATCH_MESSAGE);
	    return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(opResponse);
	}
	opResponse = changePasswordOperation.changePassword(opRequest);

	/* Code added for SMS service */
	if (opResponse != null && opResponse.isSuccess()) {
	    if (USER_MIGRATED.equalsIgnoreCase(cpCommand.getUsrStatInMCE())) {
		// If THM User update status in MCE
		LOGGER.debug("Migrated User status change to active.");
		updateStatusinMCE(context, cpCommand);
	    } else {
		changePasswordOperation.sendSMSSuccessAcknowledgment(opRequest, opResponse);
	    }/* Code ends for SMS service */
	}
	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(opResponse);
    }

    private void updateStatusinMCE(Context context, ChangePasswordCommand cpCommand) {
	LOGGER.debug("in updateStatusinMCE: for THM Migrated User ");
	UpdateDetailstoMCEOpRequest updateDetailstoMCEOpRequest = getUpdateDetailstoMCEOpRequest(context, cpCommand);
	updateDetailstoMCEOperation.updateDetailsToMCE(updateDetailstoMCEOpRequest);
	LOGGER.debug("details UPDATED IN MCE SUCCESSFULLY: ");
    }

    private UpdateDetailstoMCEOpRequest getUpdateDetailstoMCEOpRequest(Context context, ChangePasswordCommand cpCommand) {
	RetrieveAllCustAcctOperationResponse retrieveAllCustAcctOperationResponse = getUserDetailstoUpdate(context);
	CustomerDTO customer = retrieveAllCustAcctOperationResponse.getCustomer();
	UpdateDetailstoMCEOpRequest updateDetailstoMCEOpRequest = new UpdateDetailstoMCEOpRequest();
	updateDetailstoMCEOpRequest.setScvid(customer.getCustomerID());
	updateDetailstoMCEOpRequest.setAccountList(retrieveAllCustAcctOperationResponse.getAccountList());
	updateDetailstoMCEOpRequest.setContext(context);
	updateDetailstoMCEOpRequest.setMobileNo(cpCommand.getMobileNo());
	updateDetailstoMCEOpRequest.setPrefLang(customer.getLanguage());
	updateDetailstoMCEOpRequest.setRegistrationStatus("ACTIVE");
	updateDetailstoMCEOpRequest.setActionType("EDIT");
	return updateDetailstoMCEOpRequest;
    }

    private RetrieveAllCustAcctOperationResponse getUserDetailstoUpdate(Context context) {
	RetrieveAllCustAcctOperationRequest retrieveAllCustAcctOperationRequest = new RetrieveAllCustAcctOperationRequest();
	retrieveAllCustAcctOperationRequest.setContext(context);
	return retrieveAllCustAcctOperation.retrieveCustInfo(retrieveAllCustAcctOperationRequest);
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    public BMBJSONBuilder getBmbJsonBuilder() {
	return bmbJsonBuilder;
    }

    public ChangePasswordOperation getChangePasswordOperation() {
	return changePasswordOperation;
    }

    public void setChangePasswordOperation(ChangePasswordOperation changePasswordOperation) {
	this.changePasswordOperation = changePasswordOperation;
    }

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

}
