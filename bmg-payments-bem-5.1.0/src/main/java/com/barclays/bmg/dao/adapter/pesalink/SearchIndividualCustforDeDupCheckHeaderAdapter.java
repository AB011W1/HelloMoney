package com.barclays.bmg.dao.adapter.pesalink;

import com.barclays.bem.BEMServiceHeader.BEMReqHeader;
import com.barclays.bmg.constants.ServiceIdConstants;
import com.barclays.bmg.dao.adapter.request.AbstractReqAdptOperation;
import com.barclays.bmg.dao.core.context.WorkContext;


public class SearchIndividualCustforDeDupCheckHeaderAdapter extends AbstractReqAdptOperation{

		public BEMReqHeader buildRequestHeader(WorkContext workContext){

			return super.buildRequestHeader(workContext, ServiceIdConstants.SERVICE_SEARCH_INDIVIDUAL_CUST_DEDUP_CHECK);
		}
}


