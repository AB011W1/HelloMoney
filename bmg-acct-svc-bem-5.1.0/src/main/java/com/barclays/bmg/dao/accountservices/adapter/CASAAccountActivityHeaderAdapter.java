package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CASAAccountActivityHeaderAdapter extends AbstractReqAdptOperation {

    public static final String SERVICE_RETRIEVE_C_A_S_A_ACCT_TRANSACTION_ACTIVITY = "RetrieveCASAAcctTransactionActivity";

    public BEMReqHeader buildCASAAccountActivityHeader(WorkContext workContext) {

	return super.buildRequestHeader(workContext, SERVICE_RETRIEVE_C_A_S_A_ACCT_TRANSACTION_ACTIVITY);
    }

}
