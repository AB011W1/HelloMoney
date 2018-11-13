package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.AddIndividualBeneficiary.AddIndividualBeneficiaryRequest;
import com.barclays.bem.AddIndividualBeneficiary.AddIndividualBeneficiaryResponse;
import com.barclays.bem.BeneficiaryManagement.BeneficiaryManagement_PortType;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

/**
 * @author BTCI
 * Transmission Service class for Add beneficiary which calls BEM
 *         remote service
 */
public class AddBeneficiaryTransmissionService implements TransmissionService {

	private BeneficiaryManagement_PortType remoteService;

	/**
	 * @param remoteService
	 */
	public void setRemoteService(BeneficiaryManagement_PortType remoteService) {
		this.remoteService = remoteService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.barclays.bmg.dao.service.TransmissionService#sendAndReceive(java.
	 * lang.Object)
	 */
	@Override
	public Object sendAndReceive(Object object) throws Exception {

		AddIndividualBeneficiaryRequest request = (AddIndividualBeneficiaryRequest) object;
		AddIndividualBeneficiaryResponse response = null;
		try {
			response = remoteService.addIndividualBeneficiary(request);
		} catch (RemoteException e) {
			
			throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
		}
		return response;
	}

}
