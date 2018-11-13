package com.barclays.bmg.ussd.service.impl;

import java.rmi.RemoteException;

import uk.co.barclays.www.rbb.tcvm.Notification.SendEventNotificationRequestType;
import uk.co.barclays.www.rbb.tcvm.Notification.SendEventNotificationResponseType;
import uk.co.barclays.www.rbb.tcvm.NotificationService.NotificationService_PortType;

import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.dao.service.impl.AbstractTransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

/**
 * * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
 * <b>ReissuePinDAOController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>June 14, 2013</b> </br>
 * ************************************************************
 * 
 * @author E20043104
 * 
 */
public class SMSDetailsTransmissionService extends AbstractTransmissionService {

    private NotificationService_PortType remoteService;

    public NotificationService_PortType getRemoteService() {
	return remoteService;
    }

    public void setRemoteService(NotificationService_PortType remoteService) {
	this.remoteService = remoteService;
    }

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	SendEventNotificationRequestType request = (SendEventNotificationRequestType) object;

	SendEventNotificationResponseType response = null;
	try {
	    remoteService.sendNotificationOneWay(request);

	} catch (RemoteException e) {
	    
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	}

	// super.convertException(response.getResponseHeader());

	return response;
    }

    /*
     * public void setRemoteService(NotificationService_PortType remoteService) { this.remoteService = remoteService; }
     */
}
