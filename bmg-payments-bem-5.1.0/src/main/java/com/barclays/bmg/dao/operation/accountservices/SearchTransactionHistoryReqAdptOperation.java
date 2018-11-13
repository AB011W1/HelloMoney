package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.SearchFundsTransferHistory.SearchFundsTransferHistoryRequest;
import com.barclays.bmg.dao.accountservices.adapter.SearchTransactionHistoryHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.SearchTransactionHistoryPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class SearchTransactionHistoryReqAdptOperation {

	private SearchTransactionHistoryHeaderAdapter searchTransactionHistoryHeaderAdapter;
	private SearchTransactionHistoryPayloadAdapter searchTransactionHistoryPayloadAdapter;

	public void setSearchTransactionHistoryHeaderAdapter(
			SearchTransactionHistoryHeaderAdapter searchTransactionHistoryHeaderAdapter) {
		this.searchTransactionHistoryHeaderAdapter = searchTransactionHistoryHeaderAdapter;
	}

	public void setSearchTransactionHistoryPayloadAdapter(
			SearchTransactionHistoryPayloadAdapter searchTransactionHistoryPayloadAdapter) {
		this.searchTransactionHistoryPayloadAdapter = searchTransactionHistoryPayloadAdapter;
	}

	public SearchFundsTransferHistoryRequest adaptRequestforAddBeneficiary(WorkContext context){

		SearchFundsTransferHistoryRequest request = new SearchFundsTransferHistoryRequest();
		request.setRequestHeader(searchTransactionHistoryHeaderAdapter.buildRequestHeader(context));
		request.setSearchFundsTransferHistoryInfo(searchTransactionHistoryPayloadAdapter.adaptPayload(context));
		return request;
	}

}
