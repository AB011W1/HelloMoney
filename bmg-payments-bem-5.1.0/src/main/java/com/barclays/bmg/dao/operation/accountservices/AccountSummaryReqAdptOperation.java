package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveIndividualCustAcctList.RetrieveIndividualCustomerAccountListRequest;
import com.barclays.bmg.dao.accountservices.adapter.RetrieveAccountSummaryHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.RetrieveAccountSummaryPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class AccountSummaryReqAdptOperation {

    private RetrieveAccountSummaryHeaderAdapter retrieveAccountSummaryHeaderAdapter;

    private RetrieveAccountSummaryPayloadAdapter retrieveAccountSummaryPayloadAdapter;

    public RetrieveIndividualCustomerAccountListRequest adaptRequestForAccountSummary(WorkContext workContext) {

	RetrieveIndividualCustomerAccountListRequest accountListRequest = new RetrieveIndividualCustomerAccountListRequest();

	accountListRequest.setRequestHeader(retrieveAccountSummaryHeaderAdapter.buildAccountSummaryHeader(workContext));
	accountListRequest.setIndividualCustomerInfo(retrieveAccountSummaryPayloadAdapter.adaptPayLoad(workContext));

	return accountListRequest;
    }

    public void setRetrieveAccountSummaryHeaderAdapter(RetrieveAccountSummaryHeaderAdapter retrieveAccountSummaryHeaderAdapter) {
	this.retrieveAccountSummaryHeaderAdapter = retrieveAccountSummaryHeaderAdapter;
    }

    public void setRetrieveAccountSummaryPayloadAdapter(RetrieveAccountSummaryPayloadAdapter retrieveAccountSummaryPayloadAdapter) {
	this.retrieveAccountSummaryPayloadAdapter = retrieveAccountSummaryPayloadAdapter;
    }

}
