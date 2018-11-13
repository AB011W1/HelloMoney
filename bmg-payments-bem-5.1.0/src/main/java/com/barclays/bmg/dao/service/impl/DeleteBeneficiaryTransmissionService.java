package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.BeneficiaryManagement.BeneficiaryManagement_PortType;
import com.barclays.bem.DeleteIndividualCustBeneficiary.DeleteIndividualCustomerBeneficiaryRequest;
import com.barclays.bem.DeleteIndividualCustBeneficiary.DeleteIndividualCustomerBeneficiaryResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

/**
 * @author BTCI
 *
 */
public class DeleteBeneficiaryTransmissionService implements
		TransmissionService {

	private BeneficiaryManagement_PortType remoteService;

	/**
	 * @param remoteService
	 */
	public void setRemoteService(BeneficiaryManagement_PortType remoteService) {
		this.remoteService = remoteService;
	}

	/* (non-Javadoc)
	 * @see com.barclays.bmg.dao.service.TransmissionService#sendAndReceive(java.lang.Object)
	 */
	@Override
	public Object sendAndReceive(Object object) throws Exception {

		DeleteIndividualCustomerBeneficiaryRequest request = (DeleteIndividualCustomerBeneficiaryRequest) object;
		DeleteIndividualCustomerBeneficiaryResponse response = null;
		try {
			response = remoteService.deleteIndividualCustBeneficiary(request);
		} catch (RemoteException e) {
			
			throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
		}
		return response;
	}

}
