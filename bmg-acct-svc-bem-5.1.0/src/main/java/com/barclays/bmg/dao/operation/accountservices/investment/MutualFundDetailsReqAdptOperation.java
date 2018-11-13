package com.barclays.bmg.dao.operation.accountservices.investment;

import com.barclays.bem.RetrieveInvestmentAcctDetailsByCustNo.RetrieveInvestmentAccountDetailsByCustomerNoRequest;
import com.barclays.bmg.dao.accountservices.adapter.MutualFundDetailsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.MutualFundDetailsPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class MutualFundDetailsReqAdptOperation {

    private MutualFundDetailsHeaderAdapter mutualFundDetailsHeaderAdapter;

    private MutualFundDetailsPayloadAdapter mutualFundDetailsPayloadAdapter;

    public RetrieveInvestmentAccountDetailsByCustomerNoRequest adaptRequestForMutualFundDetails(WorkContext workContext) {

	RetrieveInvestmentAccountDetailsByCustomerNoRequest retInvAccDetByCusNoReq = new RetrieveInvestmentAccountDetailsByCustomerNoRequest();

	retInvAccDetByCusNoReq.setRequestHeader(mutualFundDetailsHeaderAdapter.buildMutualFundDetailsHeader(workContext));

	mutualFundDetailsPayloadAdapter.adaptPayLoad(workContext, retInvAccDetByCusNoReq);

	return retInvAccDetByCusNoReq;
    }

    public void setMutualFundDetailsHeaderAdapter(MutualFundDetailsHeaderAdapter mutualFundDetailsHeaderAdapter) {
	this.mutualFundDetailsHeaderAdapter = mutualFundDetailsHeaderAdapter;
    }

    public void setMutualFundDetailsPayloadAdapter(MutualFundDetailsPayloadAdapter mutualFundDetailsPayloadAdapter) {
	this.mutualFundDetailsPayloadAdapter = mutualFundDetailsPayloadAdapter;
    }

}
