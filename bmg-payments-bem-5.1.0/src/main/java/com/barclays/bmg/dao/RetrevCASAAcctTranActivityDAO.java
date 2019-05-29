package com.barclays.bmg.dao;

import com.barclays.bmg.service.accounts.request.RetrevCASAAcctTranActivityServiceRequest;
import com.barclays.bmg.service.accounts.response.RetrevCASAAcctTranActivityServiceResponse;

public interface RetrevCASAAcctTranActivityDAO {
	public RetrevCASAAcctTranActivityServiceResponse retrevCasaAcctTranActivity(
			RetrevCASAAcctTranActivityServiceRequest request);
}
