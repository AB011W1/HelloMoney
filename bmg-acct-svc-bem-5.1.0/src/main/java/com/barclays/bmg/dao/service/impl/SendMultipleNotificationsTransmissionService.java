package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import uk.co.barclays.www.rbb.tcvm.Notification.SendMultipleNotificationsRequestType;
import uk.co.barclays.www.rbb.tcvm.NotificationService.NotificationService_PortType;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.service.response.SendMultipleNotificationsServiceResponse;

public class SendMultipleNotificationsTransmissionService extends
		AbstractTransmissionService {

	private NotificationService_PortType remoteService;

	public NotificationService_PortType getRemoteService() {
		return remoteService;
	}

	public void setRemoteService(
			NotificationService_PortType remoteService) {
		this.remoteService = remoteService;
	}

	@Override
	public Object sendAndReceive(Object object) throws Exception {
		SendMultipleNotificationsRequestType request = (SendMultipleNotificationsRequestType) object;

		SendMultipleNotificationsServiceResponse response = null;
		try {
			remoteService.sendMultipleNotifications(request);
		} catch (RemoteException e) {

			throw new BMBDataAccessException(
					ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
		}
		return response;

	}

}
