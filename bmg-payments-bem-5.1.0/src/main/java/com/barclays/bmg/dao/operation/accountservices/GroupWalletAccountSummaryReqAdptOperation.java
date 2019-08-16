package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.RetrieveNonPersonalCustAccountList.RetrieveNonPersonalCustAccountListRequest;
import com.barclays.bem.SearchIndividualCustInformation.SearchIndividualCustomerInformationRequest;
import com.barclays.bmg.dao.accountservices.adapter.GroupWalletAccountSummaryHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.GroupWalletAccountSummaryPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class GroupWalletAccountSummaryReqAdptOperation {
	GroupWalletAccountSummaryHeaderAdapter groupWalletAccountSummaryHeaderAdapter;
	GroupWalletAccountSummaryPayloadAdapter groupWalletAccountSummaryPayloadAdapter;

	public void setGroupWalletAccountSummaryHeaderAdapter(
			GroupWalletAccountSummaryHeaderAdapter groupWalletAccountSummaryHeaderAdapter) {
		this.groupWalletAccountSummaryHeaderAdapter = groupWalletAccountSummaryHeaderAdapter;
	}
	public void setGroupWalletAccountSummaryPayloadAdapter(
			GroupWalletAccountSummaryPayloadAdapter groupWalletAccountSummaryPayloadAdapter) {
		this.groupWalletAccountSummaryPayloadAdapter = groupWalletAccountSummaryPayloadAdapter;
	}
	 //TODO Request type from BEM stub.jar
	public RetrieveNonPersonalCustAccountListRequest adaptRequest(WorkContext workContext){

		RetrieveNonPersonalCustAccountListRequest request =new RetrieveNonPersonalCustAccountListRequest();
		request.setRequestHeader(groupWalletAccountSummaryHeaderAdapter.buildRequestHeader(workContext));

		//TODO change implementation of  adaptPayLoad
		request.setAccountRetrievalCriteria(groupWalletAccountSummaryPayloadAdapter.adaptPayLoad(workContext));
		return request;

	}
}
