package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveCASAAcctTransactionActivity.RetrieveCASAAccountTransactionActivityRequest;
import com.barclays.bmg.dao.accountservices.adapter.CASAAccountActivityHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.CASAAccountActivityPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

/**
 *
 *
 */
public class CASAAccountActivityReqAdptOperation {

    private CASAAccountActivityHeaderAdapter casaAccountActivityHeaderAdapter;

    private CASAAccountActivityPayloadAdapter casaAccountActivityPayloadAdapter;

    public RetrieveCASAAccountTransactionActivityRequest adaptRequestForCASAAccountActivity(WorkContext workContext) {

	RetrieveCASAAccountTransactionActivityRequest casaAccountTransactionActivityRequest = new RetrieveCASAAccountTransactionActivityRequest();

	casaAccountTransactionActivityRequest.setRequestHeader(casaAccountActivityHeaderAdapter.buildCASAAccountActivityHeader(workContext));

	casaAccountTransactionActivityRequest.setCASAAccountTransactionSearchInfo(casaAccountActivityPayloadAdapter.adaptPayLoad(workContext));

	return casaAccountTransactionActivityRequest;
    }

    public CASAAccountActivityHeaderAdapter getCasaAccountActivityHeaderAdapter() {
	return casaAccountActivityHeaderAdapter;
    }

    public void setCasaAccountActivityHeaderAdapter(CASAAccountActivityHeaderAdapter casaAccountActivityHeaderAdapter) {
	this.casaAccountActivityHeaderAdapter = casaAccountActivityHeaderAdapter;
    }

    public CASAAccountActivityPayloadAdapter getCasaAccountActivityPayloadAdapter() {
	return casaAccountActivityPayloadAdapter;
    }

    public void setCasaAccountActivityPayloadAdapter(CASAAccountActivityPayloadAdapter casaAccountActivityPayloadAdapter) {
	this.casaAccountActivityPayloadAdapter = casaAccountActivityPayloadAdapter;
    }

}
