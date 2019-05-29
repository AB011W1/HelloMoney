package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.ManageFundtransferStatusDAO;
import com.barclays.bmg.service.ManageFundtransferStatusService;
import com.barclays.bmg.service.request.ManageFundtransferStatusServiceRequest;
import com.barclays.bmg.service.response.ManageFundtransferStatusServiceResponse;

public class ManageFundtransferStatusServiceImpl implements
		ManageFundtransferStatusService {

	ManageFundtransferStatusDAO manageFundtransferStatusDAO;

	@Override
	public ManageFundtransferStatusServiceResponse manageFundTransferStatus(
			ManageFundtransferStatusServiceRequest request) {
		// TODO Auto-generated method stub
		ManageFundtransferStatusServiceResponse response = manageFundtransferStatusDAO.manageFundTransferStatus(request);

		return response;
	}

	public ManageFundtransferStatusDAO getManageFundtransferStatusDAO() {
		return manageFundtransferStatusDAO;
	}

	public void setManageFundtransferStatusDAO(
			ManageFundtransferStatusDAO manageFundtransferStatusDAO) {
		this.manageFundtransferStatusDAO = manageFundtransferStatusDAO;
	}

}
