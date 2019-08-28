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
 * Package name is com.barclays.bmg.dao.operation.accountservices
 * /
 */
package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.EncryptCreditCardATMPin.CreditCardATMPinRequest;
import com.barclays.bmg.dao.accountservices.adapter.ThmEncryptPinHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.ThmEncryptPinPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class ThmEncryptPinReqAdaptOperation {

    private ThmEncryptPinHeaderAdapter thmEncryptPinHeaderAdapter;

    private ThmEncryptPinPayloadAdapter thmEncryptPinPayloadAdapter;

    /**
     * The method is written for Adapt request.
     * 
     * @param workContext
     *            the work context
     * @return the Object's object
     */
    public CreditCardATMPinRequest adaptRequest(WorkContext workContext) {
	CreditCardATMPinRequest encryptRequest = new CreditCardATMPinRequest();

	encryptRequest.setRequestHeader(thmEncryptPinHeaderAdapter.buildRequestHeader(workContext));
	encryptRequest.setCreditCardATMPin(thmEncryptPinPayloadAdapter.adaptPayLoad(workContext));
	return encryptRequest;

    }

    public ThmEncryptPinHeaderAdapter getThmEncryptPinHeaderAdapter() {
	return thmEncryptPinHeaderAdapter;
    }

    public void setThmEncryptPinHeaderAdapter(ThmEncryptPinHeaderAdapter thmEncryptPinHeaderAdapter) {
	this.thmEncryptPinHeaderAdapter = thmEncryptPinHeaderAdapter;
    }

    public ThmEncryptPinPayloadAdapter getThmEncryptPinPayloadAdapter() {
	return thmEncryptPinPayloadAdapter;
    }

    public void setThmEncryptPinPayloadAdapter(ThmEncryptPinPayloadAdapter thmEncryptPinPayloadAdapter) {
	this.thmEncryptPinPayloadAdapter = thmEncryptPinPayloadAdapter;
    }

}
