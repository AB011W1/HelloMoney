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

import com.barclays.bem.HMCustomer.HMAccount_Type;
import com.barclays.bem.HMCustomer.HMCustomer_Type;
import com.barclays.bem.TelephoneAddress.TelephoneAddress;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.ussd.auth.service.request.SelfRegistrationInitServiceRequest;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationInitPayloadAdapter.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class SelfRegistrationInitPayloadAdapter {

    /**
     * This method adaptPayload has the purpose to request for registration initiation.
     * 
     * @param workContext
     * @return HMCustomer_Type
     */
    public HMCustomer_Type adaptPayload(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;

	HMCustomer_Type hmCustomer = new HMCustomer_Type();
	HMAccount_Type hmAccount[] = new HMAccount_Type[1];
	TelephoneAddress telephone = new TelephoneAddress();

	Object[] args = daoContext.getArguments();

	SelfRegistrationInitServiceRequest request = (SelfRegistrationInitServiceRequest) args[0];

	telephone.setPhoneNumber(request.getMobileNo());

	hmCustomer.setTelephone(telephone);
	// hmCustomer.setCustomerNumber(request.getAccountNo());
	hmAccount[0] = new HMAccount_Type();
	hmAccount[0].setBranchCode(request.getBranchCode());
	hmAccount[0].setAccountNumber(request.getAccountNo());

	hmCustomer.setHMCustAccount(hmAccount);
	return hmCustomer;
    }

}
