package com.barclays.bmg.chequebook.service.impl;

import com.barclays.bmg.chequebook.dao.ChequeBookRequestExecuteDAO;
import com.barclays.bmg.chequebook.service.ChequeBookRequestExecuteService;
import com.barclays.bmg.chequebook.service.request.ChequeBookExecuteServiceRequest;
import com.barclays.bmg.chequebook.service.response.ChequeBookExecuteServiceResponse;
import com.barclays.bmg.service.sessionactivity.annotation.SessionActivitySupport;

public class ChequeBookRequestExecuteServiceImpl implements ChequeBookRequestExecuteService{

	ChequeBookRequestExecuteDAO chequeBookRequestExecuteDAO;

	@Override
	@SessionActivitySupport(activityType="chequeBook")
	public ChequeBookExecuteServiceResponse executeChequeBookRequest(
			ChequeBookExecuteServiceRequest request) {

		ChequeBookExecuteServiceResponse response =
					chequeBookRequestExecuteDAO.executeChequeBookRequest(request);



		return response;
	}

	public ChequeBookRequestExecuteDAO getChequeBookRequestExecuteDAO() {
		return chequeBookRequestExecuteDAO;
	}

	public void setChequeBookRequestExecuteDAO(
			ChequeBookRequestExecuteDAO chequeBookRequestExecuteDAO) {
		this.chequeBookRequestExecuteDAO = chequeBookRequestExecuteDAO;
	}

}
