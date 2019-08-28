package com.barclays.bmg.dao.operation.accountservices;

import uk.co.barclays.www.rbb.tcvm.Notification.SendMultipleNotificationsRequestType;

import com.barclays.bem.RetrieveCASAAcctTransactionActivity.RetrieveCASAAccountTransactionActivityRequest;
import com.barclays.bmg.dao.accountservices.adapter.SendMultipleNotificationsHeaderAdapter;
import com.barclays.bmg.dao.accountservices.adapter.SendMultipleNotificationsPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class SendMultipleNotificationsReqAdptOperation {
	SendMultipleNotificationsHeaderAdapter sendMultipleNotificationsHeaderAdapter;
	SendMultipleNotificationsPayloadAdapter sendMultipleNotificationsPayloadAdapter;
	public SendMultipleNotificationsHeaderAdapter getSendMultipleNotificationsHeaderAdapter() {
		return sendMultipleNotificationsHeaderAdapter;
	}
	public void setSendMultipleNotificationsHeaderAdapter(
			SendMultipleNotificationsHeaderAdapter sendMultipleNotificationsHeaderAdapter) {
		this.sendMultipleNotificationsHeaderAdapter = sendMultipleNotificationsHeaderAdapter;
	}
	public SendMultipleNotificationsPayloadAdapter getSendMultipleNotificationsPayloadAdapter() {
		return sendMultipleNotificationsPayloadAdapter;
	}
	public void setSendMultipleNotificationsPayloadAdapter(
			SendMultipleNotificationsPayloadAdapter sendMultipleNotificationsPayloadAdapter) {
		this.sendMultipleNotificationsPayloadAdapter = sendMultipleNotificationsPayloadAdapter;
	}

	public SendMultipleNotificationsRequestType adaptRequest(WorkContext workContext){

		SendMultipleNotificationsRequestType request =new SendMultipleNotificationsRequestType();
		request.setChannelId("UB");
		request.setRequestHeader(sendMultipleNotificationsHeaderAdapter.buildRequestHeader(workContext));

		//TODO change implementation of  adaptPayLoad
		request.setEventDetails(sendMultipleNotificationsPayloadAdapter.adaptPayLoad(workContext));
		return request;

	}
}
