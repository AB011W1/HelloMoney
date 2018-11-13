package com.barclays.bmg.service.impl.fundtransfer;


import com.barclays.bmg.dao.fundtransfer.DomesticFundTransferDAO;
import com.barclays.bmg.service.fundtransfer.DomesticFundTransferService;
import com.barclays.bmg.service.request.DomesticFundTransferServiceRequest;
import com.barclays.bmg.service.response.DomesticFundTransferServiceResponse;
import com.barclays.bmg.service.sessionactivity.annotation.SessionActivitySupport;

public class DomesticFundTransferServiceImpl implements DomesticFundTransferService {

	private DomesticFundTransferDAO domesticFundTransferDAO;
	@Override
	@SessionActivitySupport(activityType="fundTransfer")
	public DomesticFundTransferServiceResponse makeDomesticFundTransfer(
			DomesticFundTransferServiceRequest request) {

		return domesticFundTransferDAO.makeDomesticFundTransfer(request);
	}
	public DomesticFundTransferDAO getDomesticFundTransferDAO() {
		return domesticFundTransferDAO;
	}
	public void setDomesticFundTransferDAO(
			DomesticFundTransferDAO domesticFundTransferDAO) {
		this.domesticFundTransferDAO = domesticFundTransferDAO;
	}

}
