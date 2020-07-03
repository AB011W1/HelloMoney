package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bem.BEMServiceHeader.RoutingIndicator;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;

public class RetrieveBillDetailsHeaderAdapter extends AbstractReqAdptOperation {

	public BEMReqHeader buildRequestHeader(WorkContext workContext){
		BEMReqHeader reqHeader = super.buildRequestHeader(workContext, ServiceIdConstants.SERVICE_RETRIEVE_GEPG_BILL_DETAILS);
		//Ghana data bundle change
		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		RequestContext request = (RequestContext) args[0];
		Context context = request.getContext();
		String businessId = context.getBusinessId().toString();
		if(null != businessId && businessId.equalsIgnoreCase("GHBRB")) {
			RoutingIndicator routInd = new RoutingIndicator();
		     routInd.setIndicator("DB");
		 	 reqHeader.setRoutingIndicator(routInd); 
		}
		 
	     return reqHeader;
	}

}
