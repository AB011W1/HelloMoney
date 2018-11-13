package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.AddOrganizationBeneficiary.AddOrganizationBeneficiaryRequest;
import com.barclays.bem.AddOrganizationBeneficiary.AddOrganizationBeneficiaryResponse;
import com.barclays.bem.BeneficiaryManagement.BeneficiaryManagement_PortType;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

/**
 * @author E20041929 This service is used for new biller registration to the
 *         BEM. This is mainly designed for Biller Registration interface
 *         service for BMG applications.
 */
public class AddOrgBenDtlsTransmissionService implements TransmissionService {

	private BeneficiaryManagement_PortType remoteService;

	@Override
	public Object sendAndReceive(Object object) throws Exception {
		AddOrganizationBeneficiaryRequest request = (AddOrganizationBeneficiaryRequest) object;
		AddOrganizationBeneficiaryResponse response = null;

		try {
			response = remoteService.addOrganizationBeneficiary(request);
		} catch (RemoteException e) {
			
			// Errorcode:would be same for remote services .
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
