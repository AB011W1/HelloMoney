package com.barclays.bmg.service;

import com.barclays.bmg.service.request.StatmentExecuteServiceRequest;
import com.barclays.bmg.service.response.StatmentExecuteServiceResponse;

public interface StatementRequestExecuteService {

    public StatmentExecuteServiceResponse executeStatmentRequest(StatmentExecuteServiceRequest request);

}
