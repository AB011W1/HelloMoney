package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.InternationalFundTransferServiceRequest;
import com.barclays.bmg.service.response.InternationalFundTransferServiceResponse;

public interface InternationalFundTransferDAO {

	public InternationalFundTransferServiceResponse makeInternationFundTransfer
	(InternationalFundTransferServiceRequest request);
}
