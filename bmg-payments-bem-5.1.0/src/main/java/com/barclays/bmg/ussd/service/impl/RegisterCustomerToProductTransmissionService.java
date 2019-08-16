package com.barclays.bmg.ussd.service.impl;

import java.rmi.RemoteException;
import uk.co.barclays.www.rbb.tcvm.Notification.SendEventNotificationResponseType;
import com.barclays.bem.CommonServiceManagement.CommonServiceManagement_PortType;
import com.barclays.bem.RegisterCustomerToProductOneWay.RegisterCustomerToProductRequestOneWay;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.dao.service.impl.AbstractTransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class RegisterCustomerToProductTransmissionService extends AbstractTransmissionService {


	private CommonServiceManagement_PortType remoteService;

	 public CommonServiceManagement_PortType getRemoteService() {
			return remoteService;
		    }

	 public void setRemoteService(CommonServiceManagement_PortType remoteService) {
			this.remoteService = remoteService;
		    }


	 @Override
	    public Object sendAndReceive(Object object) throws Exception {

		 RegisterCustomerToProductRequestOneWay request=(RegisterCustomerToProductRequestOneWay) object;

		 RegisterCustomerToProductRequestOneWay response = null;
		 try {
			    remoteService.registerCustomerToProductOneWay(request);

			} catch (Exception e) {

			    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
			}
		return response;




	 }
}


