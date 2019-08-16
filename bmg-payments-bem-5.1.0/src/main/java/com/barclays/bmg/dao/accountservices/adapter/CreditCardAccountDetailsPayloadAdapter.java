package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.RetrieveCreditCardAcctDetails.CreditCardAccountInfo;
import com.barclays.bem.RetrieveCreditCardAcctDetails.RetrieveCreditCardAccountDetailsRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.accountdetails.request.CreditCardAccountDetailsServiceRequest;

public class CreditCardAccountDetailsPayloadAdapter {

    public void adaptPayLoad(WorkContext workContext, RetrieveCreditCardAccountDetailsRequest creditCardAccountDetailsRequest) {
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	CreditCardAccountDetailsServiceRequest ccdDetailsServiceRequest = (CreditCardAccountDetailsServiceRequest) args[0];

	CreditCardAccountInfo creditCardAccountInfo = new CreditCardAccountInfo();

	creditCardAccountInfo.setAccountNumber(ccdDetailsServiceRequest.getAccountNo());
	creditCardAccountInfo.setCreditCardAccountOrgCode("230");

	creditCardAccountDetailsRequest.setCreditCardAccountInfo(creditCardAccountInfo);

    }

}
