package com.barclays.bmg.ussd.dao.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class ReissuePinHeaderAdapter extends AbstractReqAdptOperation {
    /**
     * This method buildReissuePinReqHeader has the purpose to build request header
     * 
     * @param workContext
     *            void
     */
    public BEMReqHeader buildReissuePinReqHeader(WorkContext workContext) {
	return super.buildRequestHeader(workContext, ServiceIdConstants.REISSUE_PIN_FOR_REGISTRATION);
    }
}
