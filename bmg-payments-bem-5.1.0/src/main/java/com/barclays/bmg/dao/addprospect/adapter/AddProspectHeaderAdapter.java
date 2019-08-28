package com.barclays.bmg.dao.addprospect.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class AddProspectHeaderAdapter extends AbstractReqAdptOperation {

    public BEMReqHeader buildBemReqHeader(WorkContext workContext) {
	return super.buildRequestHeader(workContext, ServiceIdConstants.SERVICE_ADD_PROSPECT);
    }
}