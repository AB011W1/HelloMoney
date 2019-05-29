package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.ManageFundtransferStatusServiceRequest;
import com.barclays.bmg.service.response.ManageFundtransferStatusServiceResponse;

public interface ManageFundtransferStatusDAO {
	public ManageFundtransferStatusServiceResponse manageFundTransferStatus(
			ManageFundtransferStatusServiceRequest request);
}
