package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.BEMServiceHeader.RoutingIndicator;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CreditCardPayHeaderAdapter extends AbstractReqAdptOperation {

    public BEMReqHeader buildRequestHeader(WorkContext workContext) {

	BEMReqHeader reqHeader = super.buildRequestHeader(workContext, ServiceIdConstants.SERVICE_MAKE_CREDIT_CARD_PAYMENT);
	//Commented because issue with Credit Card Link for  Vision Plus R3 relase
	//if (businessID.equalsIgnoreCase("KEBRB")) {
	    RoutingIndicator routInd = new RoutingIndicator();
	    routInd.setIndicator(CommonConstants.ROUTING_INDICATOR);
	    reqHeader.setRoutingIndicator(routInd);
	//}
	return reqHeader;
    }

}
