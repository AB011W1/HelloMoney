package com.barclays.bmg.dao.accountservices.adapter;

import java.util.Map;

import com.barclays.bem.UAERetrieveBillDetails.BillInquiryInfo;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.InqueryBillServiceRequest;

public class InqueryBillPayloadAdapter {

	private static final String BILLER_SALIK = "Salik";
	public BillInquiryInfo adaptPayload(WorkContext workContext){


		BillInquiryInfo requestBody = new BillInquiryInfo();

		DAOContext daoContext = (DAOContext)workContext;

		Object[] args = daoContext.getArguments();

		InqueryBillServiceRequest inqueryBillServiceRequest=
									(InqueryBillServiceRequest)args[0];

		if (inqueryBillServiceRequest.isMobileProvider() && inqueryBillServiceRequest.getBillRefNo2() !=null) {
			requestBody.setConsumerReferenceNumber(inqueryBillServiceRequest.getBillRefNo1() + inqueryBillServiceRequest.getBillRefNo2());
        } else {
        	requestBody.setConsumerReferenceNumber(inqueryBillServiceRequest.getBillRefNo1());
        }
		if( BILLER_SALIK.equals(inqueryBillServiceRequest.getBillerId())){
        	Map<String, Object> contextMap = inqueryBillServiceRequest.getContext().getContextMap();
        	requestBody.setConsumerPIN(inqueryBillServiceRequest.getBillRefNo2());
        	requestBody.setChannelCode(contextMap.get(SystemParameterConstant.SALIK_PMT_CHANNEL_CODE).toString());
        	requestBody.setChannelLocationCode(contextMap.get(SystemParameterConstant.SALIK_PMT_CHANNEL_LOCATION_CODE).toString());
        }
		requestBody.setOrganizationName(inqueryBillServiceRequest.getTopUpService());
		requestBody.setOrganizationCode(inqueryBillServiceRequest.getBillerId());

		return requestBody;

	}
}
