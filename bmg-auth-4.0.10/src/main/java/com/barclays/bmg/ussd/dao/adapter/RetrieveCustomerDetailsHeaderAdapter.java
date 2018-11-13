package com.barclays.bmg.ussd.dao.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetrieveCustomerDetailsHeaderAdapter extends AbstractReqAdptOperation {

    /**
     * This method buildAuthReqHeader has the purpose to build request header
     * 
     * @param workContext
     *            void
     */
    public BEMReqHeader buildDetailsReqHeader(WorkContext workContext) {
	return super.buildRequestHeader(workContext, ServiceIdConstants.RETRIEVE_CUSTOMER_DETAILS_FOR_REGISTRATION);
    }
}
