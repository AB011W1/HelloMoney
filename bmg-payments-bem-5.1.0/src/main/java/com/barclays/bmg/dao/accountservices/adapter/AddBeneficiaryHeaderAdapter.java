package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class AddBeneficiaryHeaderAdapter extends AbstractReqAdptOperation {

	public BEMReqHeader buildRequestHeader(WorkContext workContext){

		return super.buildRequestHeader(workContext, ServiceIdConstants.SERVICE_ADD_INDIVIDUAL_CUST_BENEFICIARY);
	}

}
