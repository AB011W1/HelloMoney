package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class ChangePasswordHeaderAdapter extends AbstractReqAdptOperation {

    public BEMReqHeader buildRequestHeader(WorkContext workContext) {

	BEMReqHeader reqHeader = super.buildRequestHeader(workContext, ServiceIdConstants.SERVICE_UPDATE_PIN);

	return reqHeader;

    }

}
