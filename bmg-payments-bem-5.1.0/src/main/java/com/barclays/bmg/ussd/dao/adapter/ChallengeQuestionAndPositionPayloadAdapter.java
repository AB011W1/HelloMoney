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
package com.barclays.bmg.ussd.dao.adapter;

import com.barclays.bem.RetrieveIndividualCustQuestionnaire.RetrieveDataRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.ussd.auth.service.request.ChallengeQuestionAndPositionServiceRequest;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionPayloadAdapter.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * **********************************************************
 * 
 * @author E20043104
 * 
 */
public class ChallengeQuestionAndPositionPayloadAdapter {
    /**
     * This method adaptPayLoad has the purpose to adapt pay load for request.
     * 
     * @param workContext
     *            void
     */
    public RetrieveDataRequest adaptPayLoad(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	ChallengeQuestionAndPositionServiceRequest challengeQuestionAndPositionServiceRequest = (ChallengeQuestionAndPositionServiceRequest) args[0];
	RetrieveDataRequest bemRequest = new RetrieveDataRequest();
	bemRequest.setQuestionID(challengeQuestionAndPositionServiceRequest.getQuestionId());
	bemRequest.setLanguageCode(challengeQuestionAndPositionServiceRequest.getContext().getLanguageId());

	if (challengeQuestionAndPositionServiceRequest.getUserStatusInMCE() != null) {
	    bemRequest.setCustomerID(challengeQuestionAndPositionServiceRequest.getSingleCustViewId());
	} else {
	    bemRequest.setMobileNumber(challengeQuestionAndPositionServiceRequest.getMobileNumber());
	}

	return bemRequest;
    }
}
