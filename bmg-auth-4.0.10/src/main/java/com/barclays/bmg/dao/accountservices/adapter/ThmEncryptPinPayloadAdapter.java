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
package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.EncryptCreditCardATMPin.CreditCardATMPin;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.TacticalHelloMoneyEncryptPinServiceRequest;

public class ThmEncryptPinPayloadAdapter {

    public CreditCardATMPin adaptPayLoad(WorkContext workContext) {
	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	CreditCardATMPin pinObject = new CreditCardATMPin();

	TacticalHelloMoneyEncryptPinServiceRequest request = (TacticalHelloMoneyEncryptPinServiceRequest) args[0];

	pinObject.setClearPin(request.getPin());

	return pinObject;
    }
}