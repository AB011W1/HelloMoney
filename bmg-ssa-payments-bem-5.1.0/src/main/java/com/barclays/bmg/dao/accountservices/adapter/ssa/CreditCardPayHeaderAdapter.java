package com.barclays.bmg.dao.accountservices.adapter.ssa;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class CreditCardPayHeaderAdapter extends AbstractReqAdptOperation{


	public BEMReqHeader buildRequestHeader(WorkContext workContext){

		return super.buildRequestHeader(workContext, ServiceIdConstants.SERVICE_MAKE_CREDIT_CARD_PAYMENT);
	}

}
