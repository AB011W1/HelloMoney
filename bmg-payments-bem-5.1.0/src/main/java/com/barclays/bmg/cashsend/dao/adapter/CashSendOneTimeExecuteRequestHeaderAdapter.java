package com.barclays.bmg.cashsend.dao.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CashSendOneTimeExecuteRequestHeaderAdapter extends AbstractReqAdptOperation {

    public BEMReqHeader buildRequestHeader(WorkContext workContext) {

	BEMReqHeader reqHeader = super.buildRequestHeader(workContext, ServiceIdConstants.SEND_CASH_SERVICE);

//	// Temp Fix for sending 10 digit reference number
//	ServiceContext serviceContext = reqHeader.getServiceContext();
//	String consumerUniqueRefNo = serviceContext.getConsumerUniqueRefNo();
//
//	serviceContext.setConsumerUniqueRefNo(consumerUniqueRefNo.substring(0, 10));
//	serviceContext.setOriginalConsumerUniqueRefNo(consumerUniqueRefNo.substring(0, 10));

	return reqHeader;
    }
}
