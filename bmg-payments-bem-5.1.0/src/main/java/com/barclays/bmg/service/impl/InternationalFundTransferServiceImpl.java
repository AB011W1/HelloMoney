package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.InternationalFundTransferDAO;
import com.barclays.bmg.service.InternationalFundTransferService;
import com.barclays.bmg.service.request.InternationalFundTransferServiceRequest;
import com.barclays.bmg.service.response.InternationalFundTransferServiceResponse;
import com.barclays.bmg.service.sessionactivity.annotation.SessionActivitySupport;

public class InternationalFundTransferServiceImpl implements InternationalFundTransferService {

	private InternationalFundTransferDAO internationalFundTransferDAO;
	@Override
	@SessionActivitySupport(activityType="internationFundTransfer")
	public InternationalFundTransferServiceResponse makeInternationFundTransfer(
			InternationalFundTransferServiceRequest request) {

		return internationalFundTransferDAO.makeInternationFundTransfer(request);
	}
	public InternationalFundTransferDAO getInternationalFundTransferDAO() {
		return internationalFundTransferDAO;
	}
	public void setInternationalFundTransferDAO(
			InternationalFundTransferDAO internationalFundTransferDAO) {
		this.internationalFundTransferDAO = internationalFundTransferDAO;
	}

}
