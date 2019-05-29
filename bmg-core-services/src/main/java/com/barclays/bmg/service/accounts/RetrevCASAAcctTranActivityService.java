package com.barclays.bmg.service.accounts;

import com.barclays.bmg.service.accounts.request.RetrevCASAAcctTranActivityServiceRequest;
import com.barclays.bmg.service.accounts.response.RetrevCASAAcctTranActivityServiceResponse;


public interface RetrevCASAAcctTranActivityService {
	public RetrevCASAAcctTranActivityServiceResponse retrevCasaAcctTranActivity(RetrevCASAAcctTranActivityServiceRequest request);
}
