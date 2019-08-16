package com.barclays.bmg.dao.operation.accountservices.creditcard;

import com.barclays.bem.RetrieveCreditCardUnbilledTransactions.RetrieveCreditCardUnbilledTransactionsRequest;
import com.barclays.bmg.dao.accountservices.adapter.CreditCardUnbilledTransHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.CreditCardUnbilledTransPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CreditCardUnbilledTransReqAdptOperation {

    private CreditCardUnbilledTransHeaderAdapter creditCardUnbilledTransHeaderAdapter;

    private CreditCardUnbilledTransPayloadAdapter creditCardUnbilledTransPayloadAdapter;

    public RetrieveCreditCardUnbilledTransactionsRequest adaptRequestForCreditCardUnbilledTrans(WorkContext workContext) {

	RetrieveCreditCardUnbilledTransactionsRequest creditCardUnbilledTransRequest = new RetrieveCreditCardUnbilledTransactionsRequest();

	creditCardUnbilledTransRequest.setRequestHeader(creditCardUnbilledTransHeaderAdapter.buildCreditCardUnbilledTransHeader(workContext));

	creditCardUnbilledTransPayloadAdapter.adaptPayLoad(workContext, creditCardUnbilledTransRequest);

	return creditCardUnbilledTransRequest;
    }

    public void setCreditCardUnbilledTransHeaderAdapter(CreditCardUnbilledTransHeaderAdapter creditCardUnbilledTransHeaderAdapter) {
	this.creditCardUnbilledTransHeaderAdapter = creditCardUnbilledTransHeaderAdapter;
    }

    public void setCreditCardUnbilledTransPayloadAdapter(CreditCardUnbilledTransPayloadAdapter creditCardUnbilledTransPayloadAdapter) {
	this.creditCardUnbilledTransPayloadAdapter = creditCardUnbilledTransPayloadAdapter;
    }

}
