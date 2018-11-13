package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.FundTransferExecuteServiceRequest;
import com.barclays.bmg.service.response.FundTransferExecuteServiceResponse;

public interface FundTransferDAO {

	public FundTransferExecuteServiceResponse executeTransfer(FundTransferExecuteServiceRequest request);
}
