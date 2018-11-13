package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CreditCardTransActivityHeaderAdapter extends AbstractReqAdptOperation {

    public static final String SERVICE_RETRIEVE_CREDITCARD_ACCT_TRANSACTION_ACTIVITY = "RetrieveCreditcardAcctTransactionActivity";

    public BEMReqHeader buildCreditCardTransActivityHeader(WorkContext workContext) {

	return super.buildRequestHeader(workContext, SERVICE_RETRIEVE_CREDITCARD_ACCT_TRANSACTION_ACTIVITY);
    }

}
