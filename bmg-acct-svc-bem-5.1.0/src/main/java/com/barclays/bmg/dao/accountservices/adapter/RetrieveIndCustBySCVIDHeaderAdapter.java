package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetrieveIndCustBySCVIDHeaderAdapter extends AbstractReqAdptOperation {

    public BEMReqHeader buildBemReqHeader(WorkContext workContext) {
	return super.buildRequestHeader(workContext, ServiceIdConstants.SERVICE_RTRV_IND_CUST_BY_SCVID);
    }

}
