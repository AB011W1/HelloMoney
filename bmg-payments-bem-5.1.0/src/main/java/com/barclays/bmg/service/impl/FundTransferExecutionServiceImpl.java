package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.FundTransferDAO;
import com.barclays.bmg.service.FundTransferExecutionService;
import com.barclays.bmg.service.request.FundTransferExecuteServiceRequest;
import com.barclays.bmg.service.response.FundTransferExecuteServiceResponse;
import com.barclays.bmg.service.sessionactivity.annotation.SessionActivitySupport;

public class FundTransferExecutionServiceImpl implements
		FundTransferExecutionService {

	private FundTransferDAO fundTransferDAO;

	@Override
	@SessionActivitySupport(activityType = "fundTransfer")
	public FundTransferExecuteServiceResponse executeTransfer(
			FundTransferExecuteServiceRequest request) {

		FundTransferExecuteServiceResponse fundTransferExecuteServiceResponse = fundTransferDAO
				.executeTransfer(request);
		return fundTransferExecuteServiceResponse;
	}

	public FundTransferDAO getFundTransferDAO() {
		return fundTransferDAO;
	}

	public void setFundTransferDAO(FundTransferDAO fundTransferDAO) {
		this.fundTransferDAO = fundTransferDAO;
	}

}
