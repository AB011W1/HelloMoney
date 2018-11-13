package com.barclays.bmg.ussd.dao.operation;

import java.util.Map;

import uk.co.barclays.www.rbb.tcvm.Common.RegionalCustomerType;
import uk.co.barclays.www.rbb.tcvm.Notification.SendEventNotificationRequestType;

import com.barclays.bmg.constants.SMSConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.ussd.auth.service.request.SMSDetailsServiceRequest;
import com.barclays.bmg.ussd.dao.adapter.SMSDetailsHeaderAdapter;
import com.barclays.bmg.ussd.dao.adapter.SMSDetailsPayloadAdapter;

public class SMSDetailsReqAdptOperation {

    private SMSDetailsHeaderAdapter smsDetailsHeaderAdapter;

    private SMSDetailsPayloadAdapter smsDetailsPayloadAdapter;

    public void setSmsDetailsHeaderAdapter(SMSDetailsHeaderAdapter smsDetailsHeaderAdapter) {
	this.smsDetailsHeaderAdapter = smsDetailsHeaderAdapter;
    }

    public void setSmsDetailsPayloadAdapter(SMSDetailsPayloadAdapter smsDetailsPayloadAdapter) {
	this.smsDetailsPayloadAdapter = smsDetailsPayloadAdapter;
    }

    public final SendEventNotificationRequestType adaptRequestForSMSDetails(final WorkContext context) {

	SendEventNotificationRequestType request = new SendEventNotificationRequestType();

	request.setRequestHeader(smsDetailsHeaderAdapter.buildRequestHeader(context));
	request.setEventDetails(smsDetailsPayloadAdapter.adaptPayload(context));

	DAOContext daoContext = (DAOContext) context;
	Object[] args = daoContext.getArguments();
	SMSDetailsServiceRequest smsRequest = (SMSDetailsServiceRequest) args[0];

	/* getting value from context map */
	Map<String, Object> contextMap = smsRequest.getContext().getContextMap();
	String channelInd = (String) contextMap.get(SMSConstants.SMS_SOURCE_SYSTEM_ID);
	/* ends getting value from context map */

	request.setChannelId(channelInd);
	RegionalCustomerType customerInfo = new RegionalCustomerType();

	customerInfo.setBusinessId(smsRequest.getContext().getBusinessId());
	customerInfo.setCustomerId(smsRequest.getContext().getCustomerId());

	request.setCustomerInfo(customerInfo);

	return request;
    }
}
