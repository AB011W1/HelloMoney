package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.BEMServiceHeader.RoutingIndicator;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetrieveAccountSummaryHeaderAdapter extends AbstractReqAdptOperation {

    public static final String SERVICE_RETRIEVE_INDIVIDUAL_CUST_ACCT_LIST = "RetrieveIndividualCustAcctList";

    public BEMReqHeader buildAccountSummaryHeader(WorkContext workContext) {

	BEMReqHeader reqHeader = super.buildRequestHeader(workContext, SERVICE_RETRIEVE_INDIVIDUAL_CUST_ACCT_LIST);

	String businessID = getBusinessId(workContext);
	//Commented because issue with Credit Card Link for  Vision Plus R3 release
	//if (businessID.equalsIgnoreCase("KEBRB")) {
	    RoutingIndicator routInd = new RoutingIndicator();
	    routInd.setIndicator(CommonConstants.ROUTING_INDICATOR);
	    reqHeader.setRoutingIndicator(routInd);
	//}
	return reqHeader;
    }

}
