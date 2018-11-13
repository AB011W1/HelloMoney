package com.barclays.bmg.dao.service.impl;

import com.barclays.bem.AccountManagement.AccountManagement_PortType;
import com.barclays.bem.RetrieveInvestmentAcctDetailsByCustNo.RetrieveInvestmentAccountDetailsByCustomerNoRequest;
import com.barclays.bem.RetrieveInvestmentAcctDetailsByCustNo.RetrieveInvestmentAccountDetailsByCustomerNoResponse;
import com.barclays.bmg.dao.service.TransmissionService;

public class MutualFundDetailsTransmissionService implements TransmissionService {

    private AccountManagement_PortType remoteService;

    @Override
    public Object sendAndReceive(Object object) throws Exception {

	RetrieveInvestmentAccountDetailsByCustomerNoRequest request = (RetrieveInvestmentAccountDetailsByCustomerNoRequest) object;

	RetrieveInvestmentAccountDetailsByCustomerNoResponse response = remoteService.retrieveInvestmentAcctDetailsByCustNo(request);

	return response;
    }

    public void setRemoteService(AccountManagement_PortType remoteService) {
	this.remoteService = remoteService;
    }

}
