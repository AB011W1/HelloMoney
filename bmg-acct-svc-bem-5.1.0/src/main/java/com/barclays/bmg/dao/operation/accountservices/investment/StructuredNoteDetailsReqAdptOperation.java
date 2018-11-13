package com.barclays.bmg.dao.operation.accountservices.investment;

import com.barclays.bem.RetrieveInvestmentAcctDetailsByCustNo.RetrieveInvestmentAccountDetailsByCustomerNoRequest;
import com.barclays.bmg.dao.accountservices.adapter.StructuredNoteDetailsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.StructuredNoteDetailsPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class StructuredNoteDetailsReqAdptOperation {

    private StructuredNoteDetailsHeaderAdapter strutNoteDetailsHeaderAdapter;

    private StructuredNoteDetailsPayloadAdapter strutNoteDetailsPayloadAdapter;

    public RetrieveInvestmentAccountDetailsByCustomerNoRequest adaptRequestForStructuredNoteDetails(WorkContext workContext) {

	RetrieveInvestmentAccountDetailsByCustomerNoRequest retInvAccDetByCusNoReq = new RetrieveInvestmentAccountDetailsByCustomerNoRequest();

	/* Create the Header part for Structured Note Section */
	retInvAccDetByCusNoReq.setRequestHeader(strutNoteDetailsHeaderAdapter.buildStructuredNoteDetailsHeader(workContext));

	/* Create the PayLoad part for Structured Note Section */
	strutNoteDetailsPayloadAdapter.adaptPayLoad(workContext, retInvAccDetByCusNoReq);

	return retInvAccDetByCusNoReq;
    }

    public void setStrutNoteDetailsHeaderAdapter(StructuredNoteDetailsHeaderAdapter strutNoteDetailsHeaderAdapter) {
	this.strutNoteDetailsHeaderAdapter = strutNoteDetailsHeaderAdapter;
    }

    public void setStrutNoteDetailsPayloadAdapter(StructuredNoteDetailsPayloadAdapter strutNoteDetailsPayloadAdapter) {
	this.strutNoteDetailsPayloadAdapter = strutNoteDetailsPayloadAdapter;
    }

}
