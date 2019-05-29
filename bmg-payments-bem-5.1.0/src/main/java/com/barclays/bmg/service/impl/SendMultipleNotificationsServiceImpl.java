package com.barclays.bmg.service.impl;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.dao.SendMultipleNotificationsDAO;
import com.barclays.bmg.service.accounts.SendMultipleNotificationsService;
import com.barclays.bmg.service.request.SendMultipleNotificationsServiceRequest;
import com.barclays.bmg.service.response.SendMultipleNotificationsServiceResponse;

public class SendMultipleNotificationsServiceImpl implements
		SendMultipleNotificationsService {
	SendMultipleNotificationsDAO sendMultipleNotificationsDAO;

	public SendMultipleNotificationsDAO getSendMultipleNotificationsDAO() {
		return sendMultipleNotificationsDAO;
	}

	public void setSendMultipleNotificationsDAO(
			SendMultipleNotificationsDAO sendMultipleNotificationsDAO) {
		this.sendMultipleNotificationsDAO = sendMultipleNotificationsDAO;
	}

	@Override
	//@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_SMS_DETAILS", stepId = "1", activityType = "smsDetails")
	public SendMultipleNotificationsServiceResponse sendMultipleNotifications(
			SendMultipleNotificationsServiceRequest sendMultipleNotificationsServiceRequest) {
		// TODO Auto-generated method stub
		sendMultipleNotificationsDAO.sendMultipleNotifications(sendMultipleNotificationsServiceRequest);
		return null;
	}

}
