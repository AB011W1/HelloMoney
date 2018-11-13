package com.barclays.bmg.chequebook.dao;

import com.barclays.bmg.chequebook.service.request.ChequeBookExecuteServiceRequest;
import com.barclays.bmg.chequebook.service.response.ChequeBookExecuteServiceResponse;

public interface ChequeBookRequestExecuteDAO {

	public ChequeBookExecuteServiceResponse executeChequeBookRequest(ChequeBookExecuteServiceRequest request);

}
