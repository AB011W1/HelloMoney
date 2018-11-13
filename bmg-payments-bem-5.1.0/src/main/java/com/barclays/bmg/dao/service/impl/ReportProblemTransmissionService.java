package com.barclays.bmg.dao.service.impl;

import java.rmi.RemoteException;

import com.barclays.bem.AddProblem.AddProblemRequest;
import com.barclays.bem.AddProblem.AddProblemResponse;
import com.barclays.bem.ProblemManagement.ProblemManagement_PortType;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.service.TransmissionService;
import com.barclays.bmg.exception.BMBDataAccessException;

public class ReportProblemTransmissionService implements TransmissionService {

	private ProblemManagement_PortType  remoteService;
	@Override
	public Object sendAndReceive(Object object) throws Exception {
		AddProblemRequest request = (AddProblemRequest)object;

		AddProblemResponse response = null;
		try{
			response = remoteService.addProblem(request);
		}catch (RemoteException e) {
			
			throw new BMBDataAccessException(ResponseCodeConstants.BEM_SERVICE_EXCEPTION);
		}
		return response;

	}
	public ProblemManagement_PortType getRemoteService() {
		return remoteService;
	}
	public void setRemoteService(ProblemManagement_PortType remoteService) {
		this.remoteService = remoteService;
	}

}
