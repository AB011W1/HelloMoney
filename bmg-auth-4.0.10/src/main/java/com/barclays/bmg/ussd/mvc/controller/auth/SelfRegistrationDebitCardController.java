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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.DebitCardDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.ussd.auth.operation.request.RetrieveindividualCustCardListOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.RetrieveindividualCustCardListOperationResponse;
import com.barclays.bmg.ussd.operation.RetrieveindividualCustCardListOperation;
import com.barclays.bmg.utils.BMBCommonUtility;


/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationDebitCardController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>Jun 09, 2015</b> </br>
 * ***********************************************************
 *
 * @author G01014154
 *
 */
public class SelfRegistrationDebitCardController extends BMBAbstractCommandController {

	private static final Logger LOGGER = Logger.getLogger(SelfRegistrationDebitCardController.class);

	 private static final String debitCardRegistrationFailed = "REG0118";
    /**
     * The instance variable for activityId of type String
     */
    private String activityId;

   /**
     * The instance variable for selfRegistrationDebitCardJSONBldr of type BMBJSONBuilder
     */
    private BMBJSONBuilder selfRegistrationDebitCardJSONBldr;

    /**
     * ADDED TO INCLUDE DEBIT CARD VALIDATION IN SELF REGISTRATION
	 * The instance variable named "retrieveindividualCustCardListOperation" is created.
	 */
	private RetrieveindividualCustCardListOperation retrieveindividualCustCardListOperation;

    /**
     * Entry point into controller. Handles HTTP request. Overrides handle1 method of BMBAbstractCommandController
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {
    	logger.info("Entry SelfRegistrationDebitCardController ");
    	RetrieveindividualCustCardListOperationRequest retrieveindividualCustCardListOperationRequest = makeRequest(request);
		RetrieveindividualCustCardListOperationResponse retrieveindividualCustCardListOperationResponse=retrieveindividualCustCardListOperation.retrieveCustCardList(retrieveindividualCustCardListOperationRequest);
		 if (retrieveindividualCustCardListOperationResponse != null && retrieveindividualCustCardListOperationResponse.isSuccess()) {
			 setIntoProcessMap(request, BMGProcessConstants.DEBIT_CARD_AUTHENTICATION, BMGProcessConstants.SECOND_AUTH_RESPONSE,
					 retrieveindividualCustCardListOperationResponse.isSuccess());
			 List<String> debitCardNumberList = new ArrayList<String>();
			 List<Integer> positionList = new ArrayList<Integer>();
			 List<Date> debitCardExpiryDateList = new ArrayList<Date>();
			 for(DebitCardDTO individualCustDebitCard:retrieveindividualCustCardListOperationResponse.getDebitCardDTOList()){
				debitCardNumberList.add(individualCustDebitCard.getCardNumber());
				debitCardExpiryDateList.add(individualCustDebitCard.getCardExpiryDate());
			 }
				positionList.add(Integer.valueOf(request.getParameter("randomDebitCardNo1")));
				positionList.add(Integer.valueOf(request.getParameter("randomDebitCardNo2")));
				positionList.add(Integer.valueOf(request.getParameter("randomDebitCardNo3")));
				positionList.add(Integer.valueOf(request.getParameter("randomDebitCardNo4")));
				BMBCommonUtility bmbCommonUtility=new BMBCommonUtility();
			 if(bmbCommonUtility.validateRandomDebitCardDigits(positionList, (String)request.getParameter("debitCardNo"), debitCardNumberList) &&
					 bmbCommonUtility.validateDebitCardDigitsExpiryDate(debitCardExpiryDateList, (String)request.getParameter("debitCardExpDt"))){
				 	logger.info("validateRandomDebitCardDigits && validateDebitCardDigitsExpiryDate: true");

			 }
			 else{
				 retrieveindividualCustCardListOperationResponse.setResMsg("Invalid debitcard number or expiry date entered");
				 retrieveindividualCustCardListOperationResponse.setResCde(debitCardRegistrationFailed);
				 retrieveindividualCustCardListOperationResponse.setSuccess(false);
				 retrieveindividualCustCardListOperationResponse.setContext(retrieveindividualCustCardListOperationResponse.getContext());
			 }
		 }
		 else{
			 retrieveindividualCustCardListOperationResponse.setResMsg(retrieveindividualCustCardListOperationResponse.getResMsg());
			 retrieveindividualCustCardListOperationResponse.setResCde(retrieveindividualCustCardListOperationResponse.getResCde());
			 retrieveindividualCustCardListOperationResponse.setSuccess(false);
			 retrieveindividualCustCardListOperationResponse.setContext(retrieveindividualCustCardListOperationResponse.getContext());
		 }
	return (BMBBaseResponseModel) selfRegistrationDebitCardJSONBldr.createJSONResponse(retrieveindividualCustCardListOperationResponse);
    }

    /**
     * This method makeRequest has the purpose to construct the operation request.
     *
     * @param HttpRequest
     * @return RetrieveindividualCustCardListOperationRequest
     */
    private RetrieveindividualCustCardListOperationRequest makeRequest(HttpServletRequest request) {

    	RetrieveindividualCustCardListOperationRequest retrieveindividualCustCardListOperationRequest=new RetrieveindividualCustCardListOperationRequest();
    	String accountNo = (String) getFromProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.ACCOUNT_NO);
    	String branchCode = (String) getFromProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.BRANCH_CODE);

	Context context = createContext(request);

	retrieveindividualCustCardListOperationRequest.setAccountNo(accountNo);
	retrieveindividualCustCardListOperationRequest.setBranchCode(branchCode);
		context.setActivityId(this.activityId);
	context.getContextMap().put("randomDebitCardNo", (String)request.getParameter("randomDebitCardNo"));
	context.getContextMap().put("debitCardNo", (String)request.getParameter("debitCardNo"));
	context.getContextMap().put("debitCardExpDt", (String)request.getParameter("debitCardExpDt"));
	retrieveindividualCustCardListOperationRequest.setContext(context);
	return retrieveindividualCustCardListOperationRequest;
    }

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

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * ADDED TO INCLUDE DEBIT CARD VALIDATION IN SELF REGISTRATION
     * Getter for RetrieveindividualCustCardListOperation
     *
     *@param none
     *@return RetrieveindividualCustCardListOperation
     */
	public RetrieveindividualCustCardListOperation getRetrieveindividualCustCardListOperation() {
		return retrieveindividualCustCardListOperation;
	}

	/**
	 * ADDED TO INCLUDE DEBIT CARD VALIDATION IN SELF REGISTRATION
     * Setter for RetrieveindividualCustCardListOperation
     *
     * @param RetrieveindividualCustCardListOperation
     * @return void
     */
	public void setRetrieveindividualCustCardListOperation(
			RetrieveindividualCustCardListOperation retrieveindividualCustCardListOperation) {
		this.retrieveindividualCustCardListOperation = retrieveindividualCustCardListOperation;
	}

	public BMBJSONBuilder getSelfRegistrationDebitCardJSONBldr() {
		return selfRegistrationDebitCardJSONBldr;
	}

	public void setSelfRegistrationDebitCardJSONBldr(
			BMBJSONBuilder selfRegistrationDebitCardJSONBldr) {
		this.selfRegistrationDebitCardJSONBldr = selfRegistrationDebitCardJSONBldr;
	}


}
