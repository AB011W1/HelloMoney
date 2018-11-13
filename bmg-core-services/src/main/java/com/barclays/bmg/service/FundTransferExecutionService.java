package com.barclays.bmg.service;

import com.barclays.bmg.service.request.FundTransferExecuteServiceRequest;
import com.barclays.bmg.service.response.FundTransferExecuteServiceResponse;

public interface FundTransferExecutionService {

    public FundTransferExecuteServiceResponse executeTransfer(FundTransferExecuteServiceRequest request);
}
