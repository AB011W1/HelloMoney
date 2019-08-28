package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.NonPersonalAccountManagement.NonPersonalAccountManagement_PortType;
import com.barclays.bem.RetrieveNonPersonalCustAccountList.RetrieveNonPersonalCustAccountListRequest;
import com.barclays.bem.RetrieveNonPersonalCustAccountList.RetrieveNonPersonalCustAccountListResponse;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.exception.BMBDataAccessException;

public class GWWSTransmissionService extends AbstractTransmissionService {

	private NonPersonalAccountManagement_PortType remoteService;

	public NonPersonalAccountManagement_PortType getRemoteService() {
		return remoteService;
	}

	public void setRemoteService(
			NonPersonalAccountManagement_PortType remoteService) {
		this.remoteService = remoteService;
	}

	@Override
	public Object sendAndReceive(Object object) throws Exception {
		RetrieveNonPersonalCustAccountListRequest request = (RetrieveNonPersonalCustAccountListRequest) object;

		RetrieveNonPersonalCustAccountListResponse response = null;
		try {
			response = remoteService
					.retrieveNonPersonalCustAccountList(request);
		} catch (RemoteException e) {

			throw new BMBDataAccessException(
					ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
		}
		return response;

	}

}
