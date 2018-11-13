package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import com.barclays.bem.AccountManagement.AccountManagement_PortType;
import com.barclays.bem.OrderPaperStatement.OrderPaperStatementRequest;
import com.barclays.bem.OrderPaperStatement.OrderPaperStatementResponse;
import com.barclays.bem.TransactionResponseStatus.TransactionResponseStatus;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.exception.BMBDataAccessException;

public class StatmentExecuteTransmissionService extends AbstractTransmissionService {

    private AccountManagement_PortType remortService;

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	OrderPaperStatementRequest request = (OrderPaperStatementRequest) object;

	/* StatmetnBEMServiceRequest request = (StatmetnBEMServiceRequest)object; */
	OrderPaperStatementResponse response = null;
	try {
	    response = remortService.orderPaperStatement(request);
	    response.getChargeDetails();
	    TransactionResponseStatus status = response.getTransactionResponseStatus();
	    if (status.getTransactionDateTime() != null) {
		Calendar calendar = status.getTransactionDateTime();
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(new Date(calendar.getTimeInMillis()));
	    }

	} catch (RemoteException e) {
	    
	    throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
	}
	return response;
    }

    /**
     * @return the remortService
     */
    public AccountManagement_PortType getRemortService() {
	return remortService;
    }

    /**
     * @param remortService
     *            the remortService to set
     */
    public void setRemortService(AccountManagement_PortType remortService) {
	this.remortService = remortService;
    }

}
