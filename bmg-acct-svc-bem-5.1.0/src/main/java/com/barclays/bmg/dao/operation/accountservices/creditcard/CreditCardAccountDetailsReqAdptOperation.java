package com.barclays.bmg.dao.operation.accountservices.creditcard;

import com.barclays.bem.RetrieveCreditCardAcctDetails.RetrieveCreditCardAccountDetailsRequest;
import com.barclays.bmg.dao.accountservices.adapter.CreditCardAccountDetailsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.CreditCardAccountDetailsPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CreditCardAccountDetailsReqAdptOperation {

    private CreditCardAccountDetailsHeaderAdapter creditCardAccountDetailsHeaderAdapter;

    private CreditCardAccountDetailsPayloadAdapter creditCardAccountDetailsPayloadAdapter;

    public RetrieveCreditCardAccountDetailsRequest adaptRequestForCreditCardAccountDetails(WorkContext workContext) {

	RetrieveCreditCardAccountDetailsRequest creditCardAccountDetailsRequest = new RetrieveCreditCardAccountDetailsRequest();

	creditCardAccountDetailsRequest.setRequestHeader(creditCardAccountDetailsHeaderAdapter.buildCreditCardAccountDetailsHeader(workContext));

	creditCardAccountDetailsPayloadAdapter.adaptPayLoad(workContext, creditCardAccountDetailsRequest);

	return creditCardAccountDetailsRequest;
    }

    public void setCreditCardAccountDetailsHeaderAdapter(CreditCardAccountDetailsHeaderAdapter creditCardAccountDetailsHeaderAdapter) {
	this.creditCardAccountDetailsHeaderAdapter = creditCardAccountDetailsHeaderAdapter;
    }

    public void setCreditCardAccountDetailsPayloadAdapter(CreditCardAccountDetailsPayloadAdapter creditCardAccountDetailsPayloadAdapter) {
	this.creditCardAccountDetailsPayloadAdapter = creditCardAccountDetailsPayloadAdapter;
    }

}
