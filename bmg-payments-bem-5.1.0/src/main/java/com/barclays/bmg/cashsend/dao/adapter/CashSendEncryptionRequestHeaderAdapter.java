package com.barclays.bmg.cashsend.dao.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.BEMServiceHeader.RoutingIndicator;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CashSendEncryptionRequestHeaderAdapter extends AbstractReqAdptOperation {

    public BEMReqHeader buildRequestHeader(WorkContext workContext) {

	BEMReqHeader reqHeader = super.buildRequestHeader(workContext, ServiceIdConstants.ENCRYPTION_SERVICE);
	RoutingIndicator routInd = new RoutingIndicator();
	routInd.setIndicator("HM");
	reqHeader.setRoutingIndicator(routInd);
	return reqHeader;

    }

}
