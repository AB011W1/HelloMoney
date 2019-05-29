package com.barclays.bmg.service;

import com.barclays.bmg.service.request.ManageFundtransferStatusServiceRequest;
import com.barclays.bmg.service.response.ManageFundtransferStatusServiceResponse;

public interface ManageFundtransferStatusService {
	public ManageFundtransferStatusServiceResponse manageFundTransferStatus(ManageFundtransferStatusServiceRequest request);
}
