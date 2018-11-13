package com.barclays.bmg.dao.accountservices.adapter;

import java.util.Map;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.BEMServiceHeader.RoutingIndicator;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.service.request.PayBillServiceRequest;

public class MakePayBillHeaderAdapter extends AbstractReqAdptOperation{


	public BEMReqHeader buildRequestHeader(WorkContext workContext){

	    BEMReqHeader reqHeader =  super.buildRequestHeader(workContext, ServiceIdConstants.SERVICE_MAKE_BILL_PAYMENT_FOR_ALL);
	    DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();
		RequestContext request = (RequestContext) args[0];
		Context context = request.getContext();
	    Map<String, Object> contextMap = context.getContextMap();

	    PayBillServiceRequest payBillServiceRequest = (PayBillServiceRequest) args[0];

	    if (payBillServiceRequest.getFromAccount() instanceof CreditCardAccountDTO) {
		RoutingIndicator routInd = new RoutingIndicator();
		routInd.setIndicator(CommonConstants.ROUTING_INDICATOR);
		reqHeader.setRoutingIndicator(routInd);
		 reqHeader.getBankUserContext().setStaffID((contextMap.get(SystemParameterConstant.SERVICE_HEADER_STAFF_ID_CC).toString()));

	    }

		return reqHeader;
	}

}
