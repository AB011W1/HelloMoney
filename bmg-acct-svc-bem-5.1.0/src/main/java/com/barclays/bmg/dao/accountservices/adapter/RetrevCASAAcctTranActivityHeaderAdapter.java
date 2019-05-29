package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetrevCASAAcctTranActivityHeaderAdapter extends
		AbstractReqAdptOperation {
	public BEMReqHeader buildRequestHeader(WorkContext workContext) {

		return super.buildRequestHeader(workContext,
				ServiceIdConstants.RETRIEVE_CASA_ACCOUNT_TRANSACTION_ACTIVITY);

	}
}
