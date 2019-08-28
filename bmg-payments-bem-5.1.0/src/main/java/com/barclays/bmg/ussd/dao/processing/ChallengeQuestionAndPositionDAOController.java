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
package com.barclays.bmg.ussd.dao.processing;

import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.ussd.auth.service.request.ChallengeQuestionAndPositionServiceRequest;
import com.barclays.bmg.ussd.dao.operation.ChallengeQuestionAndPositionReqAdptOperation;
import com.barclays.bmg.ussd.dao.operation.ChallengeQuestionAndPositionResAdptOperation;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionDAOController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class ChallengeQuestionAndPositionDAOController implements Controller {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ChallengeQuestionAndPositionDAOController.class);

    /**
     * The instance variable for challengeQuestionAndPositionReqAdptOperation of type ChallengeQuestionAndPositionReqAdptOperation
     */
    private ChallengeQuestionAndPositionReqAdptOperation challengeQuestionAndPositionReqAdptOperation;

    /**
     * The instance variable for challengeQuestionAndPositionResAdptOperation of type ChallengeQuestionAndPositionResAdptOperation
     */
    private ChallengeQuestionAndPositionResAdptOperation challengeQuestionAndPositionResAdptOperation;

    private TransmissionOperation transmissionOperation;

    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    /**
     * Getter for ChallengeQuestionAndPositionReqAdptOperation
     * 
     *@param none
     *@return ChallengeQuestionAndPositionReqAdptOperation
     */
    public ChallengeQuestionAndPositionReqAdptOperation getChallengeQuestionAndPositionReqAdptOperation() {
	return challengeQuestionAndPositionReqAdptOperation;
    }

    /**
     * Setter for ChallengeQuestionAndPositionReqAdptOperation
     * 
     * @param ChallengeQuestionAndPositionReqAdptOperation
     * @return void
     */
    public void setChallengeQuestionAndPositionReqAdptOperation(
	    ChallengeQuestionAndPositionReqAdptOperation challengeQuestionAndPositionReqAdptOperation) {
	this.challengeQuestionAndPositionReqAdptOperation = challengeQuestionAndPositionReqAdptOperation;
    }

    /**
     * Getter for ChallengeQuestionAndPositionResAdptOperation
     * 
     *@param none
     *@return ChallengeQuestionAndPositionResAdptOperation
     */
    public ChallengeQuestionAndPositionResAdptOperation getChallengeQuestionAndPositionResAdptOperation() {
	return challengeQuestionAndPositionResAdptOperation;
    }

    /**
     * Setter for ChallengeQuestionAndPositionResAdptOperation
     * 
     * @param ChallengeQuestionAndPositionResAdptOperation
     * @return void
     */
    public void setChallengeQuestionAndPositionResAdptOperation(
	    ChallengeQuestionAndPositionResAdptOperation challengeQuestionAndPositionResAdptOperation) {
	this.challengeQuestionAndPositionResAdptOperation = challengeQuestionAndPositionResAdptOperation;
    }

    private boolean isCryptoCall(WorkContext workContext) throws Exception {
	LOGGER.debug("Intering into Verify User Pin DAO Controller.");
	LOGGER.debug("Crypto Stub Start for Verify User Pin DAO Controller.");
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	ChallengeQuestionAndPositionServiceRequest positionServiceRequest = (ChallengeQuestionAndPositionServiceRequest) args[0];
	Context context = positionServiceRequest.getContext();
	Map<String, Object> contextMap = context.getContextMap();
	String cryptoCall = (String) contextMap.get("SQAEnabled");
	if (cryptoCall.equalsIgnoreCase("y")) {
	    return true;
	}
	return false;

    }

    /**
     * Overrides handleRequest method of Controller
     * 
     * @param WorkContext
     * @return Object
     */
    @Override
    public Object handleRequest(WorkContext workContext) throws Exception {

	Object obj = challengeQuestionAndPositionReqAdptOperation.adaptRequestforQues(workContext);
	Object obj1 = null;
	// if(!isCryptoCall(workContext)){
	obj1 = transmissionOperation.sendAndReceive(obj);
	// }

	Object obj2 = challengeQuestionAndPositionResAdptOperation.adaptResponseforAnswer(workContext, obj1);

	return obj2;
    }

}
