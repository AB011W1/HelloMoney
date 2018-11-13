package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.StatmentRequestExecuteDAO;
import com.barclays.bmg.service.StatementRequestExecuteService;
import com.barclays.bmg.service.request.StatmentExecuteServiceRequest;
import com.barclays.bmg.service.response.StatmentExecuteServiceResponse;

public class StatementRequestExecuteServiceImpl implements StatementRequestExecuteService{


	StatmentRequestExecuteDAO statmentRequestExecuteDAO;

	@Override
//	@SessionActivitySupport(activityType="stmtReqPaper")
	public StatmentExecuteServiceResponse executeStatmentRequest(
			StatmentExecuteServiceRequest request) {
		StatmentExecuteServiceResponse  response =  statmentRequestExecuteDAO.executeStatementRequest(request);
		return response;
	}

	public StatmentRequestExecuteDAO getStatmentRequestExecuteDAO() {
		return statmentRequestExecuteDAO;
	}

	public void setStatmentRequestExecuteDAO(
			StatmentRequestExecuteDAO statmentRequestExecuteDAO) {
		this.statmentRequestExecuteDAO = statmentRequestExecuteDAO;
	}


}
