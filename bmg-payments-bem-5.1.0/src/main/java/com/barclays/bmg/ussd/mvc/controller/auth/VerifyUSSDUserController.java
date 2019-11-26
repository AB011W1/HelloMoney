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
package com.barclays.bmg.ussd.mvc.controller.auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.accountdetails.RetrieveAllCustAcctOperation;
import com.barclays.bmg.operation.request.RetrieveAllCustAcctOperationRequest;
import com.barclays.bmg.operation.response.RetrieveAllCustAcctOperationResponse;
import com.barclays.bmg.ussd.auth.operation.request.ChallengeQuestionAndPositionOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.VerifyUSSDUserOperationResponse;
import com.barclays.bmg.ussd.mvc.command.auth.ChallengeQuestionAndPositionCommand;
import com.barclays.bmg.ussd.operation.VerifyUSSDUserOperation;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>VerifyUSSDUserController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ***********************************************************
 *
 * @author E20043104
 *
 */
public class VerifyUSSDUserController extends BMBAbstractCommandController {

    private static final Logger LOGGER = Logger.getLogger(VerifyUSSDUserController.class);

    /**
     * The instance variable for challengeQuestionAndPositionJSONBldr of type BMBJSONBuilder
     */
    private BMBJSONBuilder verifyUSSDUserJSONBldr;

    /**
     * The instance variable for challengeQuestionAndPositionOperation of type ChallengeQuestionAndPositionOperation
     */
    private VerifyUSSDUserOperation verifyUSSDUserOperation;

    /**
     * The instance variable for activityId of type String
     */
    private String activityId;

    /**
     * The instance variable for verifyChallengeURL of type String
     */
    private String verifyChallengeURL;

    /**
     * The instance variable named "retrieveAllCustAcctOperation" is created.
     */
    private RetrieveAllCustAcctOperation retrieveAllCustAcctOperation;

    /**
     * Getter for activityId
     *
     *@param none
     *@return activityId
     */
    public String getActivityId() {
	return activityId;
    }

    /**
     * Setter for activityId
     *
     * @param activityId
     * @return void
     */
    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    /**
     * Entry point into controller. Handles HTTP request. Overrides handle1 method of BMBAbstractCommandController
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	LOGGER.info("into Start VerifyUSSDUserController.handle1");

	ChallengeQuestionAndPositionOperationRequest challengeQuesOperRequest = makeRequest(request);
	 //Commented as to remove extra XAS call-Prod issue
	//VerifyUSSDUserOperationResponse verifyUSSDUserOperationResponse = null;
	VerifyUSSDUserOperationResponse verifyUSSDUserOperationResponse = new VerifyUSSDUserOperationResponse();

	ChallengeQuestionAndPositionCommand challengeQuesWithPosCommand = (ChallengeQuestionAndPositionCommand) command;

	if (challengeQuesWithPosCommand != null) {
	    challengeQuesOperRequest.setQuesIdString(challengeQuesWithPosCommand.getQuestionList());
	    challengeQuesOperRequest.setMobileNumber(challengeQuesWithPosCommand.getMobileNumber());
	    setIntoProcessMap(request, BMGProcessConstants.SECOND_AUTHENTICATION, RequestConstants.MOBILE_NO, challengeQuesOperRequest
		    .getMobileNumber());

	    String mobileNo = challengeQuesWithPosCommand.getMobileNumber();
	    Context context = challengeQuesOperRequest.getContext();
	    context.setMobilePhone(mobileNo);
	    challengeQuesOperRequest.setContext(context);

	    RetrieveAllCustAcctOperationRequest retrieveAllCustAcctOperationRequest = new RetrieveAllCustAcctOperationRequest();
	    retrieveAllCustAcctOperationRequest.setContext(context);
	    // Retrieve SCVID of Customer
	    RetrieveAllCustAcctOperationResponse retrieveAllCustAcctOperationResponse = retrieveAllCustAcctOperation
		    .retrieveCustInfo(retrieveAllCustAcctOperationRequest);

	    if (retrieveAllCustAcctOperationResponse != null && retrieveAllCustAcctOperationResponse.isSuccess()) {
		CustomerDTO custDto = retrieveAllCustAcctOperationResponse.getCustomer();

		if (custDto != null) {
		    challengeQuesOperRequest.setSingleCustViewId(custDto.getCustomerID());
		    challengeQuesOperRequest.setUserStatusInMCE(custDto.getUserStatusInMCE());
		}
	    }
	    retrieveAllCustAcctOperationRequest.setContext(context);

	    //Commented as to remove extra XAS call-Prod issue
	    //verifyUSSDUserOperationResponse = verifyUSSDUserOperation.getChallengeQuestionPositions(challengeQuesOperRequest);
	    //if (verifyUSSDUserOperationResponse != null && verifyUSSDUserOperationResponse.isSuccess()) {
		if (retrieveAllCustAcctOperationResponse != null && retrieveAllCustAcctOperationResponse.isSuccess()) {
		    CustomerDTO custDto = retrieveAllCustAcctOperationResponse.getCustomer();
		    if (custDto != null) {

			verifyUSSDUserOperationResponse.setUserStatusInMCE(custDto.getUserStatusInMCE());
			verifyUSSDUserOperationResponse.setLangPref(custDto.getLanguage());
			// verifyUSSDUserOperationResponse.setCryptoPinStatus(custDto.getPinStatus());
			verifyUSSDUserOperationResponse.setCryptoPinStatus("Should Change Pin");
			verifyUSSDUserOperationResponse.setScvId(custDto.getCustomerID());
			//CR-35: Self Registration Change
			verifyUSSDUserOperationResponse.setCustomerAccessStatusCode(custDto.getCustomerAccessStatusCode());
			
			//CR-77:Customer Name Shown on Welcome page
			//verifyUSSDUserOperationResponse.setCustomerFirstName(custDto.getFullName());
			//Changed after BEM/MCE change for customer full name for KITS INC INC1009890417 
			verifyUSSDUserOperationResponse.setCustomerFirstName(custDto.getGivenName());
			
			//Setting Fullname for KITS INC INC1009890417 
			setUserMapIntoSession(request, SessionConstant.SESSION_KITS_FULL_NAME, custDto.getFullName());
			
			//Set welcome banner in customerDTO for INC INC0063990
			if(null != custDto.getWelcomeBanner())
				verifyUSSDUserOperationResponse.setBannerMessage(custDto.getWelcomeBanner());
			if(null != custDto.getBocBannerFlag())
				verifyUSSDUserOperationResponse.setBocBannerFlag(custDto.getBocBannerFlag());
		    }
		}
		 //Commented as to remove extra CAS call-Prod issue
	 //   }
	  //  else if (verifyUSSDUserOperationResponse != null && !verifyUSSDUserOperationResponse.isSuccess())
		 else if (retrieveAllCustAcctOperationResponse != null && !retrieveAllCustAcctOperationResponse.isSuccess()){
		verifyUSSDUserOperationResponse = new VerifyUSSDUserOperationResponse();
		verifyUSSDUserOperationResponse.setContext(retrieveAllCustAcctOperationResponse.getContext());
		verifyUSSDUserOperationResponse.setSuccess(false);
		getMessage(retrieveAllCustAcctOperationResponse);
	    }

	}
	LOGGER.info("into End VerifyUSSDUserController.handle1");
	return (BMBBaseResponseModel) verifyUSSDUserJSONBldr.createJSONResponse(verifyUSSDUserOperationResponse);
    }

    /**
     * This method makeRequest has the purpose to construct operation request.
     *
     * @param HttpServletRequest
     * @return ChallengeQuestionAndPositionOperationRequest
     *
     */
    private ChallengeQuestionAndPositionOperationRequest makeRequest(HttpServletRequest httpRequest) {

	ChallengeQuestionAndPositionOperationRequest request = new ChallengeQuestionAndPositionOperationRequest();
	Context context = addContext(request, httpRequest);
	request.setContext(context);
	return request;

    }

    /**
     * This method addContext has the purpose to add context to operation request.
     *
     * @param ChallengeQuestionAndPositionOperationRequest
     * @param httpRequest
     * @return Context
     */
    @SuppressWarnings("deprecation")
    private Context addContext(ChallengeQuestionAndPositionOperationRequest request, HttpServletRequest httpRequest) {

	Context context = createContext(httpRequest);
	Map<String, Object> processMap = getProcessMapFromSession(httpRequest);
	context.setActivityId(this.activityId);
	request.setContext(context);
	request.setMobileNumber((String) processMap.get(SessionConstant.SESSION_MOBILE_PHONE));
	request.setSingleCustViewId((String) processMap.get(SessionConstant.SESSION_USER_ID));
	String mobileNo = (String) getFromProcessMap(httpRequest, BMGProcessConstants.SECOND_AUTHENTICATION, RequestConstants.MOBILE_NO);
	request.setMobileNumber(mobileNo);
	return context;

    }

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * Getter for verifyChallengeURL
     *
     *@param none
     *@return verifyChallengeURL
     */
    public String getVerifyChallengeURL() {
	return verifyChallengeURL;
    }

    /**
     * Setter for verifyChallengeURL
     *
     * @param verifyChallengeURL
     * @return void
     */
    public void setVerifyChallengeURL(String verifyChallengeURL) {
	this.verifyChallengeURL = verifyChallengeURL;
    }

    /**
     * @return the verifyUSSDUserJSONBldr
     */
    public BMBJSONBuilder getVerifyUSSDUserJSONBldr() {
	return verifyUSSDUserJSONBldr;
    }

    /**
     * @param verifyUSSDUserJSONBldr
     *            the verifyUSSDUserJSONBldr to set
     */
    public void setVerifyUSSDUserJSONBldr(BMBJSONBuilder verifyUSSDUserJSONBldr) {
	this.verifyUSSDUserJSONBldr = verifyUSSDUserJSONBldr;
    }

    /**
     * @return the verifyUSSDUserOperation
     */
    public VerifyUSSDUserOperation getVerifyUSSDUserOperation() {
	return verifyUSSDUserOperation;
    }

    /**
     * @param verifyUSSDUserOperation
     *            the verifyUSSDUserOperation to set
     */
    public void setVerifyUSSDUserOperation(VerifyUSSDUserOperation verifyUSSDUserOperation) {
	this.verifyUSSDUserOperation = verifyUSSDUserOperation;
    }

    /**
     * Gets the retrieve all cust acct operation.
     *
     * @return the RetrieveAllCustAcctOperation
     */
    public RetrieveAllCustAcctOperation getRetrieveAllCustAcctOperation() {
	return retrieveAllCustAcctOperation;
    }

    /**
     * Sets values for RetrieveAllCustAcctOperation.
     *
     * @param retrieveAllCustAcctOperation
     *            the retrieve all cust acct operation
     */
    public void setRetrieveAllCustAcctOperation(RetrieveAllCustAcctOperation retrieveAllCustAcctOperation) {
	this.retrieveAllCustAcctOperation = retrieveAllCustAcctOperation;
    }
}
