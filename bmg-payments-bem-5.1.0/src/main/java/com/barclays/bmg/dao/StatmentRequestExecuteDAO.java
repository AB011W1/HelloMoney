package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.StatmentExecuteServiceRequest;
import com.barclays.bmg.service.response.StatmentExecuteServiceResponse;


public interface StatmentRequestExecuteDAO {

	public StatmentExecuteServiceResponse executeStatementRequest(StatmentExecuteServiceRequest request);

}
