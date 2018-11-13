package com.barclays.bmg.chequebook.service;

import com.barclays.bmg.chequebook.service.request.ChequeBookExecuteServiceRequest;
import com.barclays.bmg.chequebook.service.response.ChequeBookExecuteServiceResponse;

public interface ChequeBookRequestExecuteService {

	public ChequeBookExecuteServiceResponse executeChequeBookRequest(ChequeBookExecuteServiceRequest request);

}
