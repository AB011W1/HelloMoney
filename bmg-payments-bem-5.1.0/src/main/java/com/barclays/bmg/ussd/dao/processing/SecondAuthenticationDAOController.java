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
import com.barclays.bmg.ussd.auth.service.request.SecondAuthenticationServiceRequest;
import com.barclays.bmg.ussd.dao.operation.SecondAuthenticationReqAdptOperation;
import com.barclays.bmg.ussd.dao.operation.SecondAuthenticationResAdptOperation;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SecondAuthenticationDAOController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class SecondAuthenticationDAOController implements Controller {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(SecondAuthenticationDAOController.class);
    /**
     * The instance variable for secondAuthReqAdptOperation of type SecondAuthenticationReqAdptOperation
     */
    private SecondAuthenticationReqAdptOperation secondAuthReqAdptOperation;
    /**
     * The instance variable for secondAuthResAdptOperation of type SecondAuthenticationResAdptOperation
     */
    private SecondAuthenticationResAdptOperation secondAuthResAdptOperation;

    /**
     * The instance variable for transmissionOperation of type TransmissionOperation
     */
    private TransmissionOperation transmissionOperation;

    /**
     * Getter for SecondAuthenticationReqAdptOperation
     * 
     *@param none
     *@return SecondAuthenticationReqAdptOperation
     */
    public SecondAuthenticationReqAdptOperation getSecondAuthReqAdptOperation() {
	return secondAuthReqAdptOperation;
    }

    /**
     * Setter for SecondAuthenticationReqAdptOperation
     * 
     * @param SecondAuthenticationReqAdptOperation
     * @return void
     */
    public void setSecondAuthReqAdptOperation(SecondAuthenticationReqAdptOperation secondAuthReqAdptOperation) {
	this.secondAuthReqAdptOperation = secondAuthReqAdptOperation;
    }

    /**
     * Getter for SecondAuthenticationResAdptOperation
     * 
     *@param none
     *@return SecondAuthenticationResAdptOperation
     */
    public SecondAuthenticationResAdptOperation getSecondAuthResAdptOperation() {
	return secondAuthResAdptOperation;
    }

    /**
     * Setter for SecondAuthenticationResAdptOperation
     * 
     * @param SecondAuthenticationResAdptOperation
     * @return void
     */
    public void setSecondAuthResAdptOperation(SecondAuthenticationResAdptOperation secondAuthResAdptOperation) {
	this.secondAuthResAdptOperation = secondAuthResAdptOperation;
    }

    /**
     * Getter for TransmissionOperation
     * 
     *@param none
     *@return TransmissionOperation
     */
    public TransmissionOperation getTransmissionOperation() {
	return transmissionOperation;
    }

    /**
     * Setter for TransmissionOperation
     * 
     * @param TransmissionOperation
     * @return void
     */
    public void setTransmissionOperation(TransmissionOperation transmissionOperation) {
	this.transmissionOperation = transmissionOperation;
    }

    private boolean isCryptoCall(WorkContext workContext) throws Exception {
	LOGGER.debug("Intering into Verify Generate Question DAO Controller.");
	LOGGER.debug("Crypto Stub Start for Verify Generate Question DAO Controller.");
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	SecondAuthenticationServiceRequest secondAuthenticationServiceRequest = (SecondAuthenticationServiceRequest) args[0];
	Context context = secondAuthenticationServiceRequest.getContext();
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

	Object obj = secondAuthReqAdptOperation.adaptRequestforAuthentication(workContext);

	Object obj1 = null;
	// if(!isCryptoCall(workContext)){
	obj1 = transmissionOperation.sendAndReceive(obj);
	// }

	Object obj2 = secondAuthResAdptOperation.adaptResponseforAuthentication(workContext, obj1);

	return obj2;
    }

}
