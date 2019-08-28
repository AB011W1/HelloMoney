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
package com.barclays.bmg.ussd.dao.operation;

import com.barclays.bem.RetrieveIndividualCustQuestionnaire.RetrieveIndividualCustQuestionnaireRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.ussd.dao.adapter.ChallengeQuestionAndPositionHeaderAdapter;
import com.barclays.bmg.ussd.dao.adapter.ChallengeQuestionAndPositionPayloadAdapter;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ChallengeQuestionAndPositionReqAdptOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b>
 * </br> ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class ChallengeQuestionAndPositionReqAdptOperation {

    /**
     * The instance variable for challengeQuestionAndPositionHeaderAdapter of type ChallengeQuestionAndPositionHeaderAdapter
     */
    private ChallengeQuestionAndPositionHeaderAdapter challengeQuestionAndPositionHeaderAdapter;

    /**
     * The instance variable for challengeQuestionAndPositionPayloadAdapter of type ChallengeQuestionAndPositionPayloadAdapter
     */
    private ChallengeQuestionAndPositionPayloadAdapter challengeQuestionAndPositionPayloadAdapter;

    /**
     * Setter for ChallengeQuestionAndPositionHeaderAdapter
     * 
     * @param ChallengeQuestionAndPositionHeaderAdapter
     * @return void
     */
    public void setChallengeQuestionAndPositionHeaderAdapter(ChallengeQuestionAndPositionHeaderAdapter challengeQuestionAndPositionHeaderAdapter) {
	this.challengeQuestionAndPositionHeaderAdapter = challengeQuestionAndPositionHeaderAdapter;
    }

    /**
     * Setter for ChallengeQuestionAndPositionPayloadAdapter
     * 
     * @param ChallengeQuestionAndPositionPayloadAdapter
     * @return void
     */
    public void setChallengeQuestionAndPositionPayloadAdapter(ChallengeQuestionAndPositionPayloadAdapter challengeQuestionAndPositionPayloadAdapter) {
	this.challengeQuestionAndPositionPayloadAdapter = challengeQuestionAndPositionPayloadAdapter;
    }

    /**
     * This method adaptRequestforQues has the purpose to adapt service request for fetching answer positions.
     * 
     * @param context
     * @return ChallengeQuesServiceRequest
     */
    public final RetrieveIndividualCustQuestionnaireRequest adaptRequestforQues(final WorkContext context) {

	RetrieveIndividualCustQuestionnaireRequest request = new RetrieveIndividualCustQuestionnaireRequest();
	request.setRequestHeader(challengeQuestionAndPositionHeaderAdapter.buildChallengeQuestionReqHeader(context));
	request.setRetrieveDataRequest(challengeQuestionAndPositionPayloadAdapter.adaptPayLoad(context));
	return request;
    }

}
