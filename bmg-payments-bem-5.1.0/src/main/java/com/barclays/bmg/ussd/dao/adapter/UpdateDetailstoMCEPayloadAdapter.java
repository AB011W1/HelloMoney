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

import com.barclays.bem.HMUpdateCustomer.HMAccount_Type;
import com.barclays.bem.HMUpdateCustomer.UpdateCustomerRequestInfo_Type;
import com.barclays.bem.TelephoneAddress.TelephoneAddress;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.ussd.auth.service.request.UpdateDetailstoMCEServiceRequest;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>UpdateDetailstoMCEPayloadAdapter.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>November 22, 2013</b> </br>
 * ***********************************************************
 *
 * @author E20045924
 *
 */
public class UpdateDetailstoMCEPayloadAdapter {

    /**
     * This method adaptPayload has the purpose to adapt request for updating details to MCE.
     *
     * @param workContext
     * @return HMCustomer_Type
     */
    public UpdateCustomerRequestInfo_Type adaptPayload(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	UpdateDetailstoMCEServiceRequest request = (UpdateDetailstoMCEServiceRequest) args[0];
	UpdateCustomerRequestInfo_Type hmCustomer_Type = new UpdateCustomerRequestInfo_Type();

	TelephoneAddress telephone = new TelephoneAddress();
	telephone.setPhoneNumber(request.getMobileNo());

	hmCustomer_Type.setCreatedBy("SHM");
	hmCustomer_Type.setCreatedOn(Calendar.getInstance());
	hmCustomer_Type.setAuthorizedBy("SHM");
	hmCustomer_Type.setAuthorizedOn(Calendar.getInstance());
	hmCustomer_Type.setAutoAuthorize(true);
	hmCustomer_Type.setCustomerID(request.getScvid());
	hmCustomer_Type.setMobileNumber(request.getMobileNo());
	hmCustomer_Type.setRegistrationStatus(request.getRegistrationStatus());

	hmCustomer_Type.setCustomerAccessStatusCode(request.getCustomerAccessStatusCode());//CR-35 Changes

	hmCustomer_Type.setActionType(request.getActionType());
	hmCustomer_Type.setUpdateType("1");
	// hmCustomer_Type.setLanguagePreference(request.getContext().getLanguageId());
	hmCustomer_Type.setLanguagePreference(request.getPrefLang().toUpperCase());

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
		    //Change as for migrated customer for KE/ZM/GH primary flag is set to false after successful migration from SHM start
		    //hmAccount_Type[counter].setPrimaryIndicator(casaAccount.isOperativeFlag());
		    boolean primaryInd=false;
		    if(casaAccount.getPriInd()!=null && casaAccount.getPriInd().equalsIgnoreCase("Y")){
		    	primaryInd=true;
		    }
		    if(primaryInd || casaAccount.isOperativeFlag()){
		    hmAccount_Type[counter].setPrimaryIndicator(true);
		    }else{
		    	hmAccount_Type[counter].setPrimaryIndicator(false);
		    }
		  //Change as for migrated customer for KE/ZM/GH primary flag is set to false after successful migration from SHM end
		    counter++;

		}

	    }
	    hmCustomer_Type.setHMAccountType(hmAccount_Type);
	}

	return hmCustomer_Type;
    }
}
