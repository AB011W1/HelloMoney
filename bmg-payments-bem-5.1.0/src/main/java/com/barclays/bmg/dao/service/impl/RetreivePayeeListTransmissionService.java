package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.BeneficiaryManagement.BeneficiaryManagement_PortType;
import com.barclays.bem.RetrieveIndividualCustBeneficiaryList.RetrieveIndividualCustomerBeneficiaryListRequest;
import com.barclays.bem.RetrieveIndividualCustBeneficiaryList.RetrieveIndividualCustomerBeneficiaryListResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class RetreivePayeeListTransmissionService implements TransmissionService {

	private BeneficiaryManagement_PortType remoteService;
	@Override
	public Object sendAndReceive(Object object) throws Exception {

		RetrieveIndividualCustomerBeneficiaryListRequest request =
					(RetrieveIndividualCustomerBeneficiaryListRequest) object;
		RetrieveIndividualCustomerBeneficiaryListResponse response = null;
		try{
			response = remoteService.retrieveIndividualCustomerBeneficiaryList(request);
		}catch (RemoteException e) {
			
			throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
		}
		return response;
	}
	public BeneficiaryManagement_PortType getRemoteService() {
		return remoteService;
	}
	public void setRemoteService(BeneficiaryManagement_PortType remoteService) {
		this.remoteService = remoteService;
	}

}
