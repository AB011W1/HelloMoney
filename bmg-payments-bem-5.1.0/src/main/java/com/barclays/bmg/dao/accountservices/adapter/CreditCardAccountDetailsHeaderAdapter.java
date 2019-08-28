package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CreditCardAccountDetailsHeaderAdapter extends AbstractReqAdptOperation {

    public static final String SERVICE_RETRIEVE_CREDIT_CARD_ACCT_DETAILS = "RetrieveCreditCardAcctDetails";

    public BEMReqHeader buildCreditCardAccountDetailsHeader(WorkContext workContext) {

	return super.buildRequestHeader(workContext, SERVICE_RETRIEVE_CREDIT_CARD_ACCT_DETAILS);
    }
}
