package com.barclays.bmg.ussd.dao.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetrieveCustomerAccountListHeaderAdapter extends AbstractReqAdptOperation {

    /**
     * This method buildCustomerAccountHeader has the purpose to build request header
     * 
     * @param workContext
     *            void
     */
    public BEMReqHeader buildCustomerAccountHeader(WorkContext workContext) {
	return super.buildRequestHeader(workContext, ServiceIdConstants.RETRIEVE_CUSTOMER_ACCOUNTLIST_FOR_REGISTRATION);
    }
}
