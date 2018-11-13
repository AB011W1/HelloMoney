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

import com.barclays.bem.SearchIndividualCustByAcct.IndividuaCustomerSearchInfo;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.ussd.auth.service.request.SelfRegistrationExecutionServiceRequest;
import com.barclays.bem.TransactionAccount.TransactionAccount;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>SelfRegistrationExecutionPayloadAdapter.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
 * ***********************************************************
 *
 * @author E20043104
 *
 */
public class SelfRegistrationExecutionPayloadAdapter {

    /**
     * This method adaptPayload has the purpose to adapt request for registration execution.
     *
     * @param workContext
     * @return IndividuaCustomerSearchInfo
     */
    public IndividuaCustomerSearchInfo adaptPayload(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	SelfRegistrationExecutionServiceRequest request = (SelfRegistrationExecutionServiceRequest) args[0];
	IndividuaCustomerSearchInfo individuaCustomerSearchInfo = new IndividuaCustomerSearchInfo();
	individuaCustomerSearchInfo.setAccountNumber(request.getAccountNo());
	boolean boolGhipss=false;
	if(request.getContext().getOpCde()!=null && request.getContext().getOpCde().equals("OP0990")){
		individuaCustomerSearchInfo.setBankClearingCode(request.getBranchCode());
		boolGhipss=true;
	}
	if(boolGhipss==false){
		individuaCustomerSearchInfo.setBranchCode(request.getBranchCode());
	}

	return individuaCustomerSearchInfo;
    }

    public TransactionAccount adaptPayloadTransactionAccount(WorkContext workContext) {

    	DAOContext daoContext = (DAOContext) workContext;
    	Object[] args = daoContext.getArguments();
    	SelfRegistrationExecutionServiceRequest request = (SelfRegistrationExecutionServiceRequest) args[0];
    	TransactionAccount transactionAccount = new TransactionAccount();
    	transactionAccount.setAccountName(request.getContext().getFullName());
    	transactionAccount.setBankClearingCode("03");

    	return transactionAccount;
        }
}
