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

import com.barclays.bem.UpdateIndividualCustQuestionnaireStatus.UpdateIndividualCustQuestionnaireStatusRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.ussd.dao.adapter.SecondAuthenticationHeaderAdapter;
import com.barclays.bmg.ussd.dao.adapter.SecondAuthenticationPayloadAdapter;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SecondAuthenticationReqAdptOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 21, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20043104
 * 
 */
public class SecondAuthenticationReqAdptOperation {

    /**
     * The instance variable for secondAuthHeaderAdapter of type SecondAuthenticationHeaderAdapter
     */
    private SecondAuthenticationHeaderAdapter secondAuthHeaderAdapter;
    /**
     * The instance variable for secondAuthPayloadAdapter of type SecondAuthenticationPayloadAdapter
     */
    private SecondAuthenticationPayloadAdapter secondAuthPayloadAdapter;

    /**
     * Setter for SecondAuthenticationHeaderAdapter
     * 
     * @param SecondAuthenticationHeaderAdapter
     * @return void
     */
    public void setSecondAuthHeaderAdapter(SecondAuthenticationHeaderAdapter secondAuthHeaderAdapter) {
	this.secondAuthHeaderAdapter = secondAuthHeaderAdapter;
    }

    /**
     * Setter for SecondAuthenticationPayloadAdapter
     * 
     * @param SecondAuthenticationPayloadAdapter
     * @return void
     */
    public void setSecondAuthPayloadAdapter(SecondAuthenticationPayloadAdapter secondAuthPayloadAdapter) {
	this.secondAuthPayloadAdapter = secondAuthPayloadAdapter;
    }

    /**
     * This method adaptRequestforAuthentication has the purpose to adapt request for authentication.
     * 
     * @param context
     * @return SecondAuthenticationServiceRequest
     */
    public final UpdateIndividualCustQuestionnaireStatusRequest adaptRequestforAuthentication(final WorkContext context) {

	UpdateIndividualCustQuestionnaireStatusRequest request = new UpdateIndividualCustQuestionnaireStatusRequest();
	request.setRequestHeader(secondAuthHeaderAdapter.buildSecondAuthReqHeader(context));
	request.setQuestionnaireDetails(secondAuthPayloadAdapter.adaptPayload(context));
	return request;
    }

}
