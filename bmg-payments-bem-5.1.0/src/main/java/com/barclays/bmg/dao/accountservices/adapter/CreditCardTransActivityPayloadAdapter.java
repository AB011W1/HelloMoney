package com.barclays.bmg.dao.accountservices.adapter;

import java.util.Calendar;

import com.barclays.bem.AccountTransactionSearchInfo.AccountTransactionSearchInfo;
import com.barclays.bem.RetrieveCreditcardAcctTransactionActivity.RetrieveCreditcardAccountTransactionActivityRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.accountdetails.request.CreditCardAccountActivityServiceRequest;
import com.barclays.bmg.utils.ConvertUtils;

public class CreditCardTransActivityPayloadAdapter {

    public void adaptPayLoad(WorkContext workContext, RetrieveCreditcardAccountTransactionActivityRequest creditCardTransActivityRequest) {
	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	CreditCardAccountActivityServiceRequest ccTransActivityServiceReq = (CreditCardAccountActivityServiceRequest) args[0];

	AccountTransactionSearchInfo accountTransactionSearchInfo = new AccountTransactionSearchInfo();

	accountTransactionSearchInfo.setAccountNumber(ccTransActivityServiceReq.getAccountNumber());

	if (ccTransActivityServiceReq.isStatementTrxFlag()) {

	    accountTransactionSearchInfo.setStatementFlag(ccTransActivityServiceReq.isStatementTrxFlag());

	    if (ccTransActivityServiceReq.getStatementDate() != null) {
		Calendar cal = Calendar.getInstance();
		cal.setTime((ccTransActivityServiceReq.getStatementDate()));
		accountTransactionSearchInfo.setStatementDate(cal);

	    }

	} else {

	    accountTransactionSearchInfo.setStatementFlag(ccTransActivityServiceReq.isStatementTrxFlag());
	    accountTransactionSearchInfo.setStartDate(ConvertUtils.getCalendarByDate(ccTransActivityServiceReq.getStartDate()));
	    accountTransactionSearchInfo.setEndDate(ConvertUtils.getCalendarByDate(ccTransActivityServiceReq.getEndDate()));

	}
	creditCardTransActivityRequest.setCreditCardTransactionSearchInfo(accountTransactionSearchInfo);

    }

}
