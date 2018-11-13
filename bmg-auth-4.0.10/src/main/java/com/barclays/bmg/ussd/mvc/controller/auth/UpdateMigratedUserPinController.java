/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
/**
 * Package name is com.barclays.bmg.mvc.controller.auth
 * /
 */
package com.barclays.bmg.ussd.mvc.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.ChangePasswordOperation;
import com.barclays.bmg.operation.TacticalHelloMoneyVerifyUserOperation;
import com.barclays.bmg.operation.accountdetails.RetrieveAllCustAcctOperation;
import com.barclays.bmg.operation.request.ChangePasswordOperationRequest;
import com.barclays.bmg.operation.request.RetrieveAllCustAcctOperationRequest;
import com.barclays.bmg.operation.response.ChangePasswordOperationResponse;
import com.barclays.bmg.operation.response.RetrieveAllCustAcctOperationResponse;
import com.barclays.bmg.ussd.auth.operation.request.UpdateDetailstoMCEOpRequest;
import com.barclays.bmg.ussd.auth.operation.response.UpdateDetailstoMCEOperationResponse;
import com.barclays.bmg.ussd.auth.operation.response.VerifyMigratedUserOperationResponse;
import com.barclays.bmg.ussd.mvc.command.auth.VerifyMigratedUserCommand;
import com.barclays.bmg.ussd.operation.UpdateDetailstoMCEOperation;

public class UpdateMigratedUserPinController extends BMBAbstractCommandController {

    private static final Logger LOGGER = Logger.getLogger(UpdateMigratedUserPinController.class);

    private String activityId;
    private TacticalHelloMoneyVerifyUserOperation thmLgnVrfyUsrPnOperation;
    private RetrieveAllCustAcctOperation retrieveAllCustAcctOperation;
    private ChangePasswordOperation changePasswordOperation;
    private UpdateDetailstoMCEOperation updateDetailstoMCEOperation;
    private BMBJSONBuilder verifyMigratedUserJSONBldr;

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	LOGGER.debug(" End UpdateMigratedUserPinController.handle1");

	VerifyMigratedUserOperationResponse migratedUserOperationResponse = new VerifyMigratedUserOperationResponse();
	VerifyMigratedUserCommand verifyMigratedUserCommand = (VerifyMigratedUserCommand) command;

	if (verifyMigratedUserCommand != null) {
	    Context context = createContext(request);
	    context.setActivityId(ActivityConstant.LOGIN_ACTIVITY_ID);

	    String scvid = (String) getFromProcessMap(request, BMGProcessConstants.VERIFY_MIGRATED_USER,
		    RequestConstants.VERIFY_MIGRAGTED_USER_SCV_ID);
	    String mobileNumber = (String) getFromProcessMap(request, BMGProcessConstants.VERIFY_MIGRATED_USER, RequestConstants.MOBILE_NO);

	    context.setCustomerId(scvid);
	    context.setMobilePhone(mobileNumber);

	    UpdateDetailstoMCEOperationResponse operationResponse = new UpdateDetailstoMCEOperationResponse();
	    if (updateThmPinInShm(context, verifyMigratedUserCommand)) {
		LOGGER.debug("try to update in MCE");
		operationResponse = updateStatusinMCE(context, verifyMigratedUserCommand, false);
	    }
	    if (operationResponse.isSuccess()) {
		migratedUserOperationResponse.setSuccess(true);
		migratedUserOperationResponse.setResCde("00000");
	    }
	} else {
	    migratedUserOperationResponse.setContext(migratedUserOperationResponse.getContext());
	    migratedUserOperationResponse.setSuccess(false);
	    getMessage(migratedUserOperationResponse);
	}
	LOGGER.debug(" End UpdateMigratedUserPinController.handle1");

	return (BMBBaseResponseModel) verifyMigratedUserJSONBldr.createJSONResponse(migratedUserOperationResponse);
    }

    /**
     * 
     * @param context
     * @param verifyMigratedUserCommand
     * @return
     */
    private boolean updateThmPinInShm(Context context, VerifyMigratedUserCommand verifyMigratedUserCommand) {
	ChangePasswordOperationRequest cpRequest = getChangePasswordOperationRequest(context, verifyMigratedUserCommand);
	if (StringUtils.isEmpty(cpRequest.getOldPassword())) {
	    cpRequest.setOldPassword("");
	}
	ChangePasswordOperationResponse cpResponse = changePasswordOperation.changePassword(cpRequest);
	LOGGER.debug("PIN UPDATED IN SHM SUCCESSFULLY: " + cpResponse.isSuccess());
	return cpResponse.isSuccess();
    }

    /**
     * 
     * @param context
     * @param loginCommand
     * @param suspendUser
     * @return
     */
    private UpdateDetailstoMCEOperationResponse updateStatusinMCE(Context context, VerifyMigratedUserCommand loginCommand, boolean suspendUser) {
	LOGGER.debug("in updateStatusinMCE: ");
	UpdateDetailstoMCEOpRequest updateDetailstoMCEOpRequest = getUpdateDetailstoMCEOpRequest(context, loginCommand);
	if (suspendUser) {
	    updateDetailstoMCEOpRequest.setRegistrationStatus("SUSPENDED");
	}
	UpdateDetailstoMCEOperationResponse operationResponse = updateDetailstoMCEOperation.updateDetailsToMCE(updateDetailstoMCEOpRequest);
	LOGGER.debug("details UPDATED IN MCE SUCCESSFULLY: ");
	return operationResponse;
    }

    /**
     * 
     * @param context
     * @param loginCommand
     * @return
     */
    private ChangePasswordOperationRequest getChangePasswordOperationRequest(Context context, VerifyMigratedUserCommand loginCommand) {
	ChangePasswordOperationRequest opRequest = new ChangePasswordOperationRequest();
	context.setActivityId("SEC_CHG_PWD");
	opRequest.setContext(context);
	opRequest.setNewPassword(loginCommand.getPass());
	opRequest.setAuthType("SP");
	return opRequest;
    }

    /**
     * 
     * @param context
     * @param loginCommand
     * @return
     */
    private UpdateDetailstoMCEOpRequest getUpdateDetailstoMCEOpRequest(Context context, VerifyMigratedUserCommand loginCommand) {
	RetrieveAllCustAcctOperationResponse retrieveAllCustAcctOperationResponse = getUserDetailstoUpdate(context);
	CustomerDTO customer = retrieveAllCustAcctOperationResponse.getCustomer();
	UpdateDetailstoMCEOpRequest updateDetailstoMCEOpRequest = new UpdateDetailstoMCEOpRequest();
	updateDetailstoMCEOpRequest.setScvid(customer.getCustomerID());
	updateDetailstoMCEOpRequest.setAccountList(retrieveAllCustAcctOperationResponse.getAccountList());
	updateDetailstoMCEOpRequest.setContext(context);
	updateDetailstoMCEOpRequest.setMobileNo(context.getMobilePhone());
	updateDetailstoMCEOpRequest.setPrefLang(customer.getLanguage());
	updateDetailstoMCEOpRequest.setRegistrationStatus("ACTIVE");
	updateDetailstoMCEOpRequest.setActionType("EDIT");
	return updateDetailstoMCEOpRequest;
    }

    /**
     * 
     * @param context
     * @return
     */
    private RetrieveAllCustAcctOperationResponse getUserDetailstoUpdate(Context context) {
	RetrieveAllCustAcctOperationRequest retrieveAllCustAcctOperationRequest = new RetrieveAllCustAcctOperationRequest();
	retrieveAllCustAcctOperationRequest.setContext(context);
	return retrieveAllCustAcctOperation.retrieveCustInfo(retrieveAllCustAcctOperationRequest);
    }

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    public RetrieveAllCustAcctOperation getRetrieveAllCustAcctOperation() {
	return retrieveAllCustAcctOperation;
    }

    public void setRetrieveAllCustAcctOperation(RetrieveAllCustAcctOperation retrieveAllCustAcctOperation) {
	this.retrieveAllCustAcctOperation = retrieveAllCustAcctOperation;
    }

    public ChangePasswordOperation getChangePasswordOperation() {
	return changePasswordOperation;
    }

    public void setChangePasswordOperation(ChangePasswordOperation changePasswordOperation) {
	this.changePasswordOperation = changePasswordOperation;
    }

    public UpdateDetailstoMCEOperation getUpdateDetailstoMCEOperation() {
	return updateDetailstoMCEOperation;
    }

    public void setUpdateDetailstoMCEOperation(UpdateDetailstoMCEOperation updateDetailstoMCEOperation) {
	this.updateDetailstoMCEOperation = updateDetailstoMCEOperation;
    }

    public BMBJSONBuilder getVerifyMigratedUserJSONBldr() {
	return verifyMigratedUserJSONBldr;
    }

    public void setVerifyMigratedUserJSONBldr(BMBJSONBuilder verifyMigratedUserJSONBldr) {
	this.verifyMigratedUserJSONBldr = verifyMigratedUserJSONBldr;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public TacticalHelloMoneyVerifyUserOperation getThmLgnVrfyUsrPnOperation() {
	return thmLgnVrfyUsrPnOperation;
    }

    public void setThmLgnVrfyUsrPnOperation(TacticalHelloMoneyVerifyUserOperation thmLgnVrfyUsrPnOperation) {
	this.thmLgnVrfyUsrPnOperation = thmLgnVrfyUsrPnOperation;
    }

}