package com.barclays.bmg.dao.operation.accountservices.creditcard;

import com.barclays.bem.RetrieveCreditCardAcctStatementDates.RetrieveCreditCardAccountStatementDatesRequest;
import com.barclays.bmg.dao.accountservices.adapter.CreditCardStatementDatesHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.CreditCardStatementDatesPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CreditCardStatementDatesReqAdptOperation {

    private CreditCardStatementDatesHeaderAdapter creditCardStatementDatesHeaderAdapter;

    private CreditCardStatementDatesPayloadAdapter creditCardStatementDatesPayloadAdapter;

    public RetrieveCreditCardAccountStatementDatesRequest adaptRequestForCreditCardStatementDates(WorkContext workContext) {

	RetrieveCreditCardAccountStatementDatesRequest creditCardStatementDatesRequest = new RetrieveCreditCardAccountStatementDatesRequest();

	creditCardStatementDatesRequest.setRequestHeader(creditCardStatementDatesHeaderAdapter.buildCreditCardStatementDatesHeader(workContext));

	creditCardStatementDatesPayloadAdapter.adaptPayLoad(workContext, creditCardStatementDatesRequest);

	return creditCardStatementDatesRequest;
    }

    public void setCreditCardStatementDatesHeaderAdapter(CreditCardStatementDatesHeaderAdapter creditCardStatementDatesHeaderAdapter) {
	this.creditCardStatementDatesHeaderAdapter = creditCardStatementDatesHeaderAdapter;
    }

    public void setCreditCardStatementDatesPayloadAdapter(CreditCardStatementDatesPayloadAdapter creditCardStatementDatesPayloadAdapter) {
	this.creditCardStatementDatesPayloadAdapter = creditCardStatementDatesPayloadAdapter;
    }

}
