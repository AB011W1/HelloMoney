package com.barclays.bmg.dao.operation.accountservices.investment;

import com.barclays.bem.RetrieveInvestmentAcctDetailsByCustNo.RetrieveInvestmentAccountDetailsByCustomerNoRequest;
import com.barclays.bmg.dao.accountservices.adapter.BondDetailsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.BondDetailsPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class BondDetailsReqAdptOperation {

    private BondDetailsHeaderAdapter bondDetailsHeaderAdapter;

    private BondDetailsPayloadAdapter bondDetailsPayloadAdapter;

    public RetrieveInvestmentAccountDetailsByCustomerNoRequest adaptRequestForBondDetails(WorkContext workContext) {

	RetrieveInvestmentAccountDetailsByCustomerNoRequest retInvAccDetByCusNoReq = new RetrieveInvestmentAccountDetailsByCustomerNoRequest();

	retInvAccDetByCusNoReq.setRequestHeader(bondDetailsHeaderAdapter.buildBondDetailsHeader(workContext));

	bondDetailsPayloadAdapter.adaptPayLoad(workContext, retInvAccDetByCusNoReq);

	return retInvAccDetByCusNoReq;
    }

    public void setBondDetailsHeaderAdapter(BondDetailsHeaderAdapter bondDetailsHeaderAdapter) {
	this.bondDetailsHeaderAdapter = bondDetailsHeaderAdapter;
    }

    public void setBondDetailsPayloadAdapter(BondDetailsPayloadAdapter bondDetailsPayloadAdapter) {
	this.bondDetailsPayloadAdapter = bondDetailsPayloadAdapter;
    }

}
