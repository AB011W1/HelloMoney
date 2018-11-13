package com.barclays.bmg.dao.operation.accountservices.creditcard;

import com.barclays.bem.RetrieveCreditcardAcctTransactionActivity.RetrieveCreditcardAccountTransactionActivityRequest;
import com.barclays.bmg.dao.accountservices.adapter.CreditCardTransActivityHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.CreditCardTransActivityPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CreditCardTransActivityReqAdptOperation {

    private CreditCardTransActivityHeaderAdapter creditCardTransActivityHeaderAdapter;

    private CreditCardTransActivityPayloadAdapter creditCardTransActivityPayloadAdapter;

    public RetrieveCreditcardAccountTransactionActivityRequest adaptRequestForCreditCardTransActivity(WorkContext workContext) {

	RetrieveCreditcardAccountTransactionActivityRequest creditCardTransActivityRequest = new RetrieveCreditcardAccountTransactionActivityRequest();

	creditCardTransActivityRequest.setRequestHeader(creditCardTransActivityHeaderAdapter.buildCreditCardTransActivityHeader(workContext));

	creditCardTransActivityPayloadAdapter.adaptPayLoad(workContext, creditCardTransActivityRequest);

	return creditCardTransActivityRequest;
    }

    public void setCreditCardTransActivityHeaderAdapter(CreditCardTransActivityHeaderAdapter creditCardTransActivityHeaderAdapter) {
	this.creditCardTransActivityHeaderAdapter = creditCardTransActivityHeaderAdapter;
    }

    public void setCreditCardTransActivityPayloadAdapter(CreditCardTransActivityPayloadAdapter creditCardTransActivityPayloadAdapter) {
	this.creditCardTransActivityPayloadAdapter = creditCardTransActivityPayloadAdapter;
    }

}
