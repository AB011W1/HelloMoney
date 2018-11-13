package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.barclays.bem.AccountReporting.AccountReporting_PortType;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveCreditcardAcctTransactionActivity.RetrieveCreditcardAccountTransactionActivityRequest;
import com.barclays.bem.RetrieveCreditcardAcctTransactionActivity.RetrieveCreditcardAccountTransactionActivityResponse;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.exception.BMBDataAccessException;

public class CreditCardTransActivityTransmissionService extends AbstractTransmissionService {

    private AccountReporting_PortType remoteService;
    private static final Logger LOGGER = Logger.getLogger(CreditCardTransActivityTransmissionService.class);

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	RetrieveCreditcardAccountTransactionActivityRequest request = (RetrieveCreditcardAccountTransactionActivityRequest) object;

	RetrieveCreditcardAccountTransactionActivityResponse response = null;
	try {
	    response = remoteService.retrieveCreditcardAcctTransactionActivity(request);

	} catch (RemoteException e) {
	    LOGGER.error("Error in BMB data access", e);
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	} catch (Error e) {
	    LOGGER.error("Error in BMB data access", e);
	    throw new BMBDataAccessException(AccountServiceResponseCodeConstant.BEM_SERVICE_EXCEPTION);
	}

	// super.convertException(response.getResponseHeader());

	return response;
    }

    public void setRemoteService(AccountReporting_PortType remoteService) {
	this.remoteService = remoteService;
    }

}
