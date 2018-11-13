package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.RetrieveAcctTransactionHistory.AcctTransactionHistorySearchInfo;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.accountdetails.request.CASAAccountActivityServiceRequest;

/*
 * Reference
 * com.barclays.mcfe.ssc.retail.core.service.md.ssa.RetailCASAAccountServiceMDImpl
 */
public class CASAAccountTrnxHistoryPayloadAdapter {

    public AcctTransactionHistorySearchInfo adaptPayLoad(WorkContext workContext) {

	AcctTransactionHistorySearchInfo requestBody = new AcctTransactionHistorySearchInfo();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	CASAAccountActivityServiceRequest casaAccountActivityServiceRequest = (CASAAccountActivityServiceRequest) args[0];

	requestBody.setAccountNumber(casaAccountActivityServiceRequest.getAccountNo());

	return requestBody;

    }
}
