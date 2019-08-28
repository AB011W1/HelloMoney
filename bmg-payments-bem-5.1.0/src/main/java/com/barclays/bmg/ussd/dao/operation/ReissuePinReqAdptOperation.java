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

import com.barclays.bem.CreatePIN.CreatePINRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.ussd.dao.adapter.ReissuePinHeaderAdapter;
import com.barclays.bmg.ussd.dao.adapter.ReissuePinPayloadAdapter;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ReissuePinReqAdptOperation.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>June 14, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20043104
 * 
 */

public class ReissuePinReqAdptOperation {
    /**
     * The instance variable for reissuePinHeaderAdapter of type ReissuePinHeaderAdapter
     */
    private ReissuePinHeaderAdapter reissuePinHeaderAdapter;

    /**
     * The instance variable for reissuePinPayloadAdapter of type ReissuePinPayloadAdapter
     */
    private ReissuePinPayloadAdapter reissuePinPayloadAdapter;

    /**
     * This method adaptRequestForReissuePin has the purpose to adapt the request for resissuing pin.
     * 
     * @param WorkContext
     * @return CreatePINRequest
     */
    public final CreatePINRequest adaptRequestForReissuePin(final WorkContext context) {

	CreatePINRequest request = new CreatePINRequest();
	request.setRequestHeader(reissuePinHeaderAdapter.buildReissuePinReqHeader(context));
	request.setCreatePIN(reissuePinPayloadAdapter.adaptPayload(context));

	return request;
    }

    /**
     * Setter for ReissuePinHeaderAdapter
     * 
     * @param ReissuePinHeaderAdapter
     * @return void
     */

    public void setReissuePinHeaderAdapter(ReissuePinHeaderAdapter reissuePinHeaderAdapter) {
	this.reissuePinHeaderAdapter = reissuePinHeaderAdapter;
    }

    /**
     * Setter for ReissuePinPayloadAdapter
     * 
     * @param ReissuePinPayloadAdapter
     * @return void
     */

    public void setReissuePinPayloadAdapter(ReissuePinPayloadAdapter reissuePinPayloadAdapter) {
	this.reissuePinPayloadAdapter = reissuePinPayloadAdapter;
    }
}
