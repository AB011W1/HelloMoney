package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class StructuredNoteDetailsHeaderAdapter extends AbstractReqAdptOperation {

    public static final String SERVICE_RETRIEVE_INVESTMENT_LIST = "RetrieveInvestmentAcctDetailsByCustNo";

    public BEMReqHeader buildStructuredNoteDetailsHeader(WorkContext workContext) {

	return super.buildRequestHeader(workContext, SERVICE_RETRIEVE_INVESTMENT_LIST);
    }
}
