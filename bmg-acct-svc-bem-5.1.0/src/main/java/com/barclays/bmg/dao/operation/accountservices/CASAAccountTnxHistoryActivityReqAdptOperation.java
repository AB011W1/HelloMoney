package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveAcctTransactionHistory.RetrieveAcctTransactionHistoryRequest;
import com.barclays.bmg.dao.accountservices.adapter.CASAAccountTrnxHistoryActivityHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.CASAAccountTrnxHistoryPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CASAAccountTnxHistoryActivityReqAdptOperation {

    private CASAAccountTrnxHistoryActivityHeaderAdapter accountTrnxHistoryActivityHeaderAdapter;

    private CASAAccountTrnxHistoryPayloadAdapter accountTrnxHistoryPayloadAdapter;

    public RetrieveAcctTransactionHistoryRequest adaptRequestForCASAAccountTrnxHistoryActivity(WorkContext workContext) {

	RetrieveAcctTransactionHistoryRequest retrieveAcctTransactionHistoryRequest = new RetrieveAcctTransactionHistoryRequest();

	retrieveAcctTransactionHistoryRequest.setRequestHeader(accountTrnxHistoryActivityHeaderAdapter
		.buildCASAAccountTrnxHistoryActivityHeader(workContext));

	retrieveAcctTransactionHistoryRequest.setAcctTransactionHistorySearchInfo(accountTrnxHistoryPayloadAdapter.adaptPayLoad(workContext));

	return retrieveAcctTransactionHistoryRequest;
    }

    public CASAAccountTrnxHistoryActivityHeaderAdapter getAccountTrnxHistoryActivityHeaderAdapter() {
	return accountTrnxHistoryActivityHeaderAdapter;
    }

    public void setAccountTrnxHistoryActivityHeaderAdapter(CASAAccountTrnxHistoryActivityHeaderAdapter accountTrnxHistoryActivityHeaderAdapter) {
	this.accountTrnxHistoryActivityHeaderAdapter = accountTrnxHistoryActivityHeaderAdapter;
    }

    public CASAAccountTrnxHistoryPayloadAdapter getAccountTrnxHistoryPayloadAdapter() {
	return accountTrnxHistoryPayloadAdapter;
    }

    public void setAccountTrnxHistoryPayloadAdapter(CASAAccountTrnxHistoryPayloadAdapter accountTrnxHistoryPayloadAdapter) {
	this.accountTrnxHistoryPayloadAdapter = accountTrnxHistoryPayloadAdapter;
    }

}
