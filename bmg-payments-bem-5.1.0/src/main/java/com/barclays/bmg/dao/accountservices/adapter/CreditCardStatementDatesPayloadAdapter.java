package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.RetrieveCreditCardAcctStatementDates.CreditCardAccountNumberInfo;
import com.barclays.bem.RetrieveCreditCardAcctStatementDates.RetrieveCreditCardAccountStatementDatesRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.accountdetails.request.CreditCardStatementDatesServiceRequest;

public class CreditCardStatementDatesPayloadAdapter {

    public void adaptPayLoad(WorkContext workContext, RetrieveCreditCardAccountStatementDatesRequest retrCCStatementDatesReq) {
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	CreditCardStatementDatesServiceRequest ccStmtDatesServiceReq = (CreditCardStatementDatesServiceRequest) args[0];

	CreditCardAccountNumberInfo ccAccountNumberInfo = new CreditCardAccountNumberInfo();
	ccAccountNumberInfo.setAccountNumber(ccStmtDatesServiceReq.getAccountNo());

	ccAccountNumberInfo.setCreditCardAccountOrgCode(ccStmtDatesServiceReq.getOrgCode());
	retrCCStatementDatesReq.setCreditCardAccountNumberInfo(ccAccountNumberInfo);
    }

}
