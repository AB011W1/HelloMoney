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

import java.util.Calendar;
import java.util.List;

import com.barclays.bem.HMCustomer.HMAccount_Type;
import com.barclays.bem.HMCustomer.HMCustomer_Type;
import com.barclays.bem.TelephoneAddress.TelephoneAddress;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.ussd.auth.service.request.AddDetailstoMCEServiceRequest;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>AddDetailstoMCEPayloadAdapter.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 * 
 * @author E20043104
 * 
 */
public class AddDetailstoMCEPayloadAdapter {

    private static final String SHM = "SHM";

    /**
     * This method adaptPayload has the purpose to adapt request for adding details to MCE.
     * 
     * @param workContext
     * @return HMCustomer_Type
     */
    public HMCustomer_Type adaptPayload(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	AddDetailstoMCEServiceRequest request = (AddDetailstoMCEServiceRequest) args[0];
	HMCustomer_Type hmCustomer_Type = new HMCustomer_Type();

	TelephoneAddress telephone = new TelephoneAddress();
	telephone.setPhoneNumber(request.getMobileNo());

	hmCustomer_Type.setCreatedBy(SHM);
	hmCustomer_Type.setCreatedOn(Calendar.getInstance());
	hmCustomer_Type.setAuthorizedBy(SHM);
	hmCustomer_Type.setAuthorizedOn(Calendar.getInstance());
	hmCustomer_Type.setAutoAuthorize(true);
	hmCustomer_Type.setTelephone(telephone);

	hmCustomer_Type.setLanguage(request.getPrefLang().toUpperCase());
	hmCustomer_Type.setSCVID(request.getScvid());
	hmCustomer_Type.setCustomerAccessStatusCode(request.getCustomerAccessStatusCode());//CR-35 Changes

	List<? extends CustomerAccountDTO> casaList = request.getAccountList();
	if (casaList != null) {

	    HMAccount_Type hmAccount_Type[] = new HMAccount_Type[casaList.size()];

	    int counter = 0;

	    for (CustomerAccountDTO accounts : casaList) {

		if (accounts instanceof CASAAccountDTO) {

		    CASAAccountDTO casaAccount = (CASAAccountDTO) accounts;
		    hmAccount_Type[counter] = new HMAccount_Type();
		    hmAccount_Type[counter].setAccountNumber(casaAccount.getAccountNumber());
		    hmAccount_Type[counter].setBranchCode(casaAccount.getBranchCode());
		    hmAccount_Type[counter].setPrimaryIndicator(casaAccount.isOperativeFlag());
		    counter++;

		}

	    }
	    hmCustomer_Type.setHMCustAccount(hmAccount_Type);
	}

	return hmCustomer_Type;
    }
}
