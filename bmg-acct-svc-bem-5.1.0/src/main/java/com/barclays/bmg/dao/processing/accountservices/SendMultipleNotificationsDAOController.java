package com.barclays.bmg.dao.processing.accountservices;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.processing.Controller;
import com.barclays.bmg.dao.operation.TransmissionOperation;
import com.barclays.bmg.dao.operation.accountservices.RetrevCASAAcctTranActivityRespAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.SendMultipleNotificationsReqAdptOperation;
import com.barclays.bmg.dao.operation.accountservices.SendMultipleNotificationsRespAdptOperation;

public class SendMultipleNotificationsDAOController implements Controller {
	SendMultipleNotificationsReqAdptOperation sendMultipleNotificationsReqAdptOperation;
	TransmissionOperation sendMultipleNotificationsTransmissionOperation;
	SendMultipleNotificationsRespAdptOperation sendMultipleNotificationsRespAdptOperation;

	public SendMultipleNotificationsReqAdptOperation getSendMultipleNotificationsReqAdptOperation() {
		return sendMultipleNotificationsReqAdptOperation;
	}

	public void setSendMultipleNotificationsReqAdptOperation(
			SendMultipleNotificationsReqAdptOperation sendMultipleNotificationsReqAdptOperation) {
		this.sendMultipleNotificationsReqAdptOperation = sendMultipleNotificationsReqAdptOperation;
	}

	public TransmissionOperation getSendMultipleNotificationsTransmissionOperation() {
		return sendMultipleNotificationsTransmissionOperation;
	}

	public void setSendMultipleNotificationsTransmissionOperation(
			TransmissionOperation sendMultipleNotificationsTransmissionOperation) {
		this.sendMultipleNotificationsTransmissionOperation = sendMultipleNotificationsTransmissionOperation;
	}

	public SendMultipleNotificationsRespAdptOperation getSendMultipleNotificationsRespAdptOperation() {
		return sendMultipleNotificationsRespAdptOperation;
	}

	public void setSendMultipleNotificationsRespAdptOperation(
			SendMultipleNotificationsRespAdptOperation sendMultipleNotificationsRespAdptOperation) {
		this.sendMultipleNotificationsRespAdptOperation = sendMultipleNotificationsRespAdptOperation;
	}

	@Override
	public Object handleRequest(WorkContext workContext) throws Exception {
		// TODO Auto-generated method stub
		Object obj = sendMultipleNotificationsReqAdptOperation.adaptRequest(workContext);

		Object obj1 = sendMultipleNotificationsTransmissionOperation.sendAndReceive(obj);

		Object obj2 = sendMultipleNotificationsRespAdptOperation.adaptResponse(workContext, obj1);

		return null;
	}

}