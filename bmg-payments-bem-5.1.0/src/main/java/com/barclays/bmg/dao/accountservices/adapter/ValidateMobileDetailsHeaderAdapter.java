package com.barclays.bmg.dao.accountservices.adapter;


import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;


public class ValidateMobileDetailsHeaderAdapter extends AbstractReqAdptOperation {
	
	public BEMReqHeader buildBemReqHeader(WorkContext workContext) {
		return super.buildRequestHeader(workContext, ServiceIdConstants.RETRIEVE_MOBILE_DETAILS);
	}

}
