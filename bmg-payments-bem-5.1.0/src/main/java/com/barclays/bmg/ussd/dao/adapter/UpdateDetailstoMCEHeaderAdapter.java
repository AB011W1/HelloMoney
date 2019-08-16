package com.barclays.bmg.ussd.dao.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class UpdateDetailstoMCEHeaderAdapter extends AbstractReqAdptOperation {
    /**
     * This method buildMCEHeader has the purpose to build request header
     * 
     * @param workContext
     *            void
     */
    public BEMReqHeader buildMCEHeader(WorkContext workContext) {
	return super.buildRequestHeader(workContext, ServiceIdConstants.UPDATE_DETAILS_TO_MCE_FOR_REGISTRATION);
    }
}
