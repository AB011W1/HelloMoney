package com.barclays.bmg.dao.accountservices.adapter;


import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;

public class FundTransferExecutionHeaderAdapter extends AbstractReqAdptOperation {


		public BEMReqHeader buildFundTransferRequestHeader(WorkContext workContext){

			return super.buildRequestHeader(workContext, ServiceIdConstants.SERVICE_MAKE_DOMESTIC_FUND_TRANSFER);
		}
}
