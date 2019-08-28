package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.AccountTransactionSearchInfo.AccountTransactionSearchInfo;
import com.barclays.bem.BEMBaseDataTypes.TransactionSearchTypeCode;
import com.barclays.bem.RetrieveCreditCardUnbilledTransactions.RetrieveCreditCardUnbilledTransactionsRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.accountdetails.request.CreditCardUnbilledTransServiceRequest;

public class CreditCardUnbilledTransPayloadAdapter {

    public void adaptPayLoad(WorkContext workContext, RetrieveCreditCardUnbilledTransactionsRequest retrCCUnbilledTransactionsReq) {
	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();

	CreditCardUnbilledTransServiceRequest ccTransActivityServiceReq = (CreditCardUnbilledTransServiceRequest) args[0];

	AccountTransactionSearchInfo accTransactionSearchInfo = new AccountTransactionSearchInfo();
	accTransactionSearchInfo.setAccountNumber(ccTransActivityServiceReq.getAccountNo());
	accTransactionSearchInfo.setTransactionSearchTypeCode(TransactionSearchTypeCode.POS);

	retrCCUnbilledTransactionsReq.setCreditCardUnbilledTransactionsSearchInfo(accTransactionSearchInfo);

    }

}
