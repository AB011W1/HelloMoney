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
package com.barclays.bmg.mvc.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.auth.LoginCommand;
import com.barclays.bmg.operation.ChangePasswordOperation;
import com.barclays.bmg.operation.TacticalHelloMoneyVerifyUserOperation;
import com.barclays.bmg.operation.UssdLgnVrfyUsrPnOperation;
import com.barclays.bmg.operation.accountdetails.RetrieveAllCustAcctOperation;
import com.barclays.bmg.operation.request.ChangePasswordOperationRequest;
import com.barclays.bmg.operation.request.RetrieveAllCustAcctOperationRequest;
import com.barclays.bmg.operation.request.TacticalHelloMoneyVerifyUserOperationRequest;
import com.barclays.bmg.operation.request.UssdLgnVrfyUsrPnOperationRequest;
import com.barclays.bmg.operation.response.ChangePasswordOperationResponse;
import com.barclays.bmg.operation.response.RetrieveAllCustAcctOperationResponse;
import com.barclays.bmg.operation.response.TacticalHelloMoneyVerifyUserOperationResponse;
import com.barclays.bmg.operation.response.UssdLgnVrfyUsrPnOperationResponse;
import com.barclays.bmg.ussd.auth.operation.request.UpdateDetailstoMCEOpRequest;
import com.barclays.bmg.ussd.operation.UpdateDetailstoMCEOperation;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>UssdLgnVrfyUsrPnController.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>June 11, 2013</b> </br>
 * ******************************************************
 *
 * @author e20037686 </br> * The Class UssdLgnVrfyUsrPnController created using JRE 1.6.0_10
 */
public class UssdLgnVrfyUsrPnController extends BMBAbstractCommandController {
    /**
     * The instance variable named "ussdLgnVrfyUsrPnOperation" is created.
     */
    private static final String USER_MIGRATED = "MIGRATED";
    private static final String ENABLED = "Enabled";
    private UssdLgnVrfyUsrPnOperation ussdLgnVrfyUsrPnOperation;
    private TacticalHelloMoneyVerifyUserOperation thmLgnVrfyUsrPnOperation;
    private RetrieveAllCustAcctOperation retrieveAllCustAcctOperation;
    private ChangePasswordOperation changePasswordOperation;
    private UpdateDetailstoMCEOperation updateDetailstoMCEOperation;

    private static final Logger LOGGER = Logger.getLogger(UssdLgnVrfyUsrPnController.class);

    public RetrieveAllCustAcctOperation getRetrieveAllCustAcctOperation() {
	return retrieveAllCustAcctOperation;
    }

    public void setRetrieveAllCustAcctOperation(RetrieveAllCustAcctOperation retrieveAllCustAcctOperation) {
	this.retrieveAllCustAcctOperation = retrieveAllCustAcctOperation;
    }

    /**
     * The instance variable named "bmbJsonBuilder" is created.
     */
    private BMBJSONBuilder bmbJsonBuilder;
    private BMBJSONBuilder bmbJsonBuilder1;

    public BMBJSONBuilder getBmbJsonBuilder1() {
	return bmbJsonBuilder1;
    }

    public void setBmbJsonBuilder1(BMBJSONBuilder bmbJsonBuilder1) {
	this.bmbJsonBuilder1 = bmbJsonBuilder1;
    }

    /**
     * The instance variable named "retrieveAllCustAccLstURL" is created.
     */
    private String retrieveAllCustAccLstURL;

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1 (javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {
	LoginCommand loginCommand = (LoginCommand) command;
	setUserMapIntoSession(request, SessionConstant.SESSION_LOGIN_ID, loginCommand.getUsrNam());
	Context context = createContext(request);

	if (USER_MIGRATED.equalsIgnoreCase(loginCommand.getUsrStatInMCE()) && !ENABLED.equalsIgnoreCase(loginCommand.getPinStatInCrypto())) {
	    // if (true) {
	    // Pin status can be 'Should Change Pin' or 'Expired'
	    return verifyPinInTacticalHelloMoney(request, loginCommand, context);
	} else {
	    return verifyPinInStrategicHelloMoney(request, loginCommand, context);
	}
    }

    private BMBBaseResponseModel verifyPinInStrategicHelloMoney(HttpServletRequest request, LoginCommand loginCommand, Context context) {
	UssdLgnVrfyUsrPnOperationRequest opReq = makeRequest(request, context);
	opReq.setUsrNam(loginCommand.getUsrNam());
	opReq.setPass(loginCommand.getPass());
	UssdLgnVrfyUsrPnOperationResponse opRes = ussdLgnVrfyUsrPnOperation.verifyUserWithPin(opReq);

	if (opRes != null && opRes.isSuccess()) {
	    setValuesInSession(request, loginCommand.getUsrNam());
	}

	if (USER_MIGRATED.equalsIgnoreCase(loginCommand.getUsrStatInMCE())) {
	    LOGGER.debug("Migrated User status change to active failed last time. Try updating now");
	    updateStatusinMCE(context, loginCommand, false);
	}
	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(opRes);
    }

    private BMBBaseResponseModel verifyPinInTacticalHelloMoney(HttpServletRequest request, LoginCommand loginCommand, Context context) {
	LOGGER.debug("UsrStatInMCE = MIGRATED & PinStatInCryp = SHUD_CHNG ==> Validate from THM");

	TacticalHelloMoneyVerifyUserOperationRequest thmRequest = new TacticalHelloMoneyVerifyUserOperationRequest();
	thmRequest.setCustMSISDN(loginCommand.getUsrNam());
	thmRequest.setCustPIN(loginCommand.getPass());
	context.setActivityId(ActivityConstant.LOGIN_ACTIVITY_ID);
	context.setMobilePhone(loginCommand.getUsrNam());
	thmRequest.setContext(context);
	TacticalHelloMoneyVerifyUserOperationResponse thmOpResp = thmLgnVrfyUsrPnOperation.verifyUserWithPin(thmRequest);

	if (thmOpResp != null) {
	    if (thmOpResp.isSuccess()) {
		LOGGER.debug("Response from THM: " + thmOpResp.getResCde());
		setValuesInSession(request, loginCommand.getUsrNam());
		// Code commented so as to forward THM migrated customer for pin change
		/*
		 * try { if (updateThmPinInShm(context, loginCommand)) { LOGGER.debug("try to update in MCE"); updateStatusinMCE(context,
		 * loginCommand, false); } } catch (Exception e) { LOGGER.error("Error while creating migrated user", e); return
		 * (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(thmOpResp); }
		 */

	    } else {
		// in case the user gets suspended or blacklisted in THM, update the status to SUSPENDED in MCE

		String resCode = thmOpResp.getResCde();
		if ("THM002".equals(resCode) || "THM005".equals(resCode) || "THM003".equals(resCode)) {
		    updateStatusinMCE(context, loginCommand, true);
		}
	    }
	}

	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(thmOpResp);
    }

    private void setValuesInSession(HttpServletRequest request, String userName) {
	setUserMapIntoSession(request, SessionConstant.SESSION_MOBILE_PHONE, userName);
	setUserMapIntoSession(request, SessionConstant.SESSION_LOGIN_ID, userName);
	setUserMapIntoSession(request, SessionConstant.SESSION_USER_ID, userName);
	BMBContextHolder.getContext().setMobilePhone(userName);
    }

    /**
     * The method is written for Make request.
     *
     * @param request
     *            the request
     * @param context
     *            the context
     * @return the UssdLgnVrfyUsrPnOperationRequest's object
     */
    private UssdLgnVrfyUsrPnOperationRequest makeRequest(HttpServletRequest request, Context context) {
	UssdLgnVrfyUsrPnOperationRequest opReq = new UssdLgnVrfyUsrPnOperationRequest();
	context.setActivityId(ActivityConstant.LOGIN_ACTIVITY_ID);
	opReq.setContext(context);
	return opReq;
    }

    /**
     * Set only the new password and call the changepassword operation.
     * */
    private ChangePasswordOperationRequest getChangePasswordOperationRequest(Context context, LoginCommand loginCommand) {
	ChangePasswordOperationRequest opRequest = new ChangePasswordOperationRequest();
	context.setActivityId("SEC_CHG_PWD");
	opRequest.setContext(context);
	opRequest.setNewPassword(loginCommand.getPass());
	opRequest.setAuthType("SP");
	context.setMobilePhone(loginCommand.getUsrNam());
	return opRequest;
    }

    private UpdateDetailstoMCEOpRequest getUpdateDetailstoMCEOpRequest(Context context, LoginCommand loginCommand) {
	RetrieveAllCustAcctOperationResponse retrieveAllCustAcctOperationResponse = getUserDetailstoUpdate(context);
	CustomerDTO customer = retrieveAllCustAcctOperationResponse.getCustomer();
	UpdateDetailstoMCEOpRequest updateDetailstoMCEOpRequest = new UpdateDetailstoMCEOpRequest();
	updateDetailstoMCEOpRequest.setScvid(customer.getCustomerID());
	updateDetailstoMCEOpRequest.setAccountList(retrieveAllCustAcctOperationResponse.getAccountList());
	updateDetailstoMCEOpRequest.setContext(context);
	updateDetailstoMCEOpRequest.setMobileNo(loginCommand.getUsrNam());
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

    private boolean updateThmPinInShm(Context context, LoginCommand loginCommand) {
	ChangePasswordOperationRequest cpRequest = getChangePasswordOperationRequest(context, loginCommand);
	ChangePasswordOperationResponse cpResponse = changePasswordOperation.changePassword(cpRequest);
	LOGGER.debug("PIN UPDATED IN SHM SUCCESSFULLY: " + cpResponse.isSuccess());
	return cpResponse.isSuccess();
    }

    private void updateStatusinMCE(Context context, LoginCommand loginCommand, boolean suspendUser) {
	LOGGER.debug("in updateStatusinMCE: ");
	UpdateDetailstoMCEOpRequest updateDetailstoMCEOpRequest = getUpdateDetailstoMCEOpRequest(context, loginCommand);
	if (suspendUser) {
	    updateDetailstoMCEOpRequest.setRegistrationStatus("SUSPEND");
	}
	updateDetailstoMCEOperation.updateDetailsToMCE(updateDetailstoMCEOpRequest);
	LOGGER.debug("details UPDATED IN MCE SUCCESSFULLY: ");
    }

    /*
     * (non-Javadoc)
     *
     * @seecom.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController# getActivityId(java.lang.Object)
     */
    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * Gets the retrieve all cust acc lst url.
     *
     * @return the RetrieveAllCustAccLstURL
     */
    public String getRetrieveAllCustAccLstURL() {
	return retrieveAllCustAccLstURL;
    }

    /**
     * Sets values for RetrieveAllCustAccLstURL.
     *
     * @param retrieveAllCustAccLstURL
     *            the retrieve all cust acc lst url
     */
    public void setRetrieveAllCustAccLstURL(String retrieveAllCustAccLstURL) {
	this.retrieveAllCustAccLstURL = retrieveAllCustAccLstURL;
    }

    /**
     * Gets the ussd lgn vrfy usr pn operation.
     *
     * @return the UssdLgnVrfyUsrPnOperation
     */
    public UssdLgnVrfyUsrPnOperation getUssdLgnVrfyUsrPnOperation() {
	return ussdLgnVrfyUsrPnOperation;
    }

    /**
     * Sets values for UssdLgnVrfyUsrPnOperation.
     *
     * @param ussdLgnVrfyUsrPnOperation
     *            the ussd lgn vrfy usr pn operation
     */
    public void setUssdLgnVrfyUsrPnOperation(UssdLgnVrfyUsrPnOperation ussdLgnVrfyUsrPnOperation) {
	this.ussdLgnVrfyUsrPnOperation = ussdLgnVrfyUsrPnOperation;
    }

    /**
     * Gets the bmb json builder.
     *
     * @return the BmbJsonBuilder
     */
    public BMBJSONBuilder getBmbJsonBuilder() {
	return bmbJsonBuilder;
    }

    /**
     * Sets values for BmbJsonBuilder.
     *
     * @param bmbJsonBuilder
     *            the bmb json builder
     */
    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    public TacticalHelloMoneyVerifyUserOperation getThmLgnVrfyUsrPnOperation() {
	return thmLgnVrfyUsrPnOperation;
    }

    public void setThmLgnVrfyUsrPnOperation(TacticalHelloMoneyVerifyUserOperation thmLgnVrfyUsrPnOperation) {
	this.thmLgnVrfyUsrPnOperation = thmLgnVrfyUsrPnOperation;
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
}