package com.barclays.bmg.dao.accountservices.adapter;

import java.util.Calendar;

import com.barclays.bem.AccountTransactionSearchInfo.AccountTransactionSearchInfo;
import com.barclays.bem.RetrieveCreditcardAcctTransactionActivity.RetrieveCreditcardAccountTransactionActivityRequest;
import com.barclays.bem.RetrieveCreditcardAcctTransactionActivity.TransActvFlagsType;
import com.barclays.bmg.constants.CommonConstants;
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
	accountTransactionSearchInfo.setTransactionTypeCode("R");

	if (ccTransActivityServiceReq.isStatementTrxFlag()) {

	    accountTransactionSearchInfo.setStatementFlag(ccTransActivityServiceReq.isStatementTrxFlag());
	    //Cards Migration Date Sequence Number
	    if (null != ccTransActivityServiceReq.getSequenceNumber()) {
	    	accountTransactionSearchInfo.setNumberTxnMonths(ccTransActivityServiceReq.getSequenceNumber());
	    }

	    if (ccTransActivityServiceReq.getStatementDate() != null) {
		Calendar cal = Calendar.getInstance();
		cal.setTime((ccTransActivityServiceReq.getStatementDate()));
		accountTransactionSearchInfo.setStatementDate(cal);

	    }
	    
	    accountTransactionSearchInfo.setStatementFlag(true);
		//accountTransactionSearchInfo.setRequireRewardsPointsFlag(true);
		accountTransactionSearchInfo
				.setMerged(CommonConstants.MERGED_VALUE);

		creditCardTransActivityRequest
				.setCreditCardTransactionSearchInfo(accountTransactionSearchInfo);

		//creditCardTransActivityRequest.setCreditCardAccountOrgCode(ccTransActivityServiceReq.getOrgCode());
		//ccTransActivityServiceReq.getOrgCode()

		TransActvFlagsType txnActvFlgType = new TransActvFlagsType();
		txnActvFlgType.setStatementTxnFlag(true);
		//txnActvFlgType.setLastFiveTxnFlag(false);
		//txnActvFlgType.setIsStatementPointsRequired(true);
		//txnActvFlgType.setIsBillingAddressRequired(false);
		creditCardTransActivityRequest.setTransActvFlags(txnActvFlgType);

	} else {

	    accountTransactionSearchInfo.setStatementFlag(ccTransActivityServiceReq.isStatementTrxFlag());
	    accountTransactionSearchInfo.setStartDate(ConvertUtils.getCalendarByDate(ccTransActivityServiceReq.getStartDate()));
	    accountTransactionSearchInfo.setEndDate(ConvertUtils.getCalendarByDate(ccTransActivityServiceReq.getEndDate()));

	}
	creditCardTransActivityRequest.setCreditCardTransactionSearchInfo(accountTransactionSearchInfo);

    }

}
