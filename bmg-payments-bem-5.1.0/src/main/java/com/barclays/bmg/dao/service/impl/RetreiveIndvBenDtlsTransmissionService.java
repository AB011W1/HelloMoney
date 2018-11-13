package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.BeneficiaryManagement.BeneficiaryManagement_PortType;
import com.barclays.bem.RetrieveIndividualBeneficiaryDetails.RetrieveIndividualBeneficiaryDetailsRequest;
import com.barclays.bem.RetrieveIndividualBeneficiaryDetails.RetrieveIndividualBeneficiaryDetailsResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class RetreiveIndvBenDtlsTransmissionService implements TransmissionService {

	private BeneficiaryManagement_PortType remoteService;
	@Override
	public Object sendAndReceive(Object object) throws Exception {


		RetrieveIndividualBeneficiaryDetailsRequest retrieveIndividualBeneficiaryDetailsRequest =
						(RetrieveIndividualBeneficiaryDetailsRequest) object;
		RetrieveIndividualBeneficiaryDetailsResponse response = null;
		try{
			 response =
				remoteService.retrieveIndividualBeneficiaryDetails(retrieveIndividualBeneficiaryDetailsRequest);
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
