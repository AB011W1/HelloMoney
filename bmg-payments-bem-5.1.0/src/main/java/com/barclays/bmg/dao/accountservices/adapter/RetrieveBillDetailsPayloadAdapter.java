package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.RetrieveBillDetails.BillInquiryInfo;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.RetrieveBillDetailsServiceRequest;

public class RetrieveBillDetailsPayloadAdapter {

	public BillInquiryInfo adaptPayload(WorkContext workContext){

		BillInquiryInfo billInquiryInfo = new BillInquiryInfo();
		DAOContext daoContext = (DAOContext)workContext;
		Object[] args = daoContext.getArguments();

		RetrieveBillDetailsServiceRequest retrieveBillDetailsServiceRequest = (RetrieveBillDetailsServiceRequest)args[0];
		billInquiryInfo.setPrimaryReferenceNumber(retrieveBillDetailsServiceRequest.getControlNumber());
		billInquiryInfo.setOrganizationCode(retrieveBillDetailsServiceRequest.getBillerID());

		return billInquiryInfo;
	}

}
