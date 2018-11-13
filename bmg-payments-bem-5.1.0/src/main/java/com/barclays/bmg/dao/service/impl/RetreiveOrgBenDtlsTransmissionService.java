package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.BeneficiaryManagement.BeneficiaryManagement_PortType;
import com.barclays.bem.RetrieveOrganizationBeneficiaryDetails.RetrieveOrganizationBeneficiaryDetailsRequest;
import com.barclays.bem.RetrieveOrganizationBeneficiaryDetails.RetrieveOrganizationBeneficiaryDetailsResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class RetreiveOrgBenDtlsTransmissionService implements TransmissionService {

	private BeneficiaryManagement_PortType remoteService;
	@Override
	public Object sendAndReceive(Object object) throws Exception {

		RetrieveOrganizationBeneficiaryDetailsRequest request = (RetrieveOrganizationBeneficiaryDetailsRequest)object;
		RetrieveOrganizationBeneficiaryDetailsResponse response = null;
		try{
			response = remoteService.retrieveOrganizationBeneficiaryDetails(request);
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
