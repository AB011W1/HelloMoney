package com.barclays.bmg.service.USSDAuthentication;

import com.barclays.bmg.service.request.USSDAuthentication.GetChangeRecordsRequest;
import com.barclays.bmg.service.response.USSDAuthentication.AuthenticateChangeServiceResponse;

public interface AuthenticateChangeRequestService {

	public AuthenticateChangeServiceResponse retrieveChangeRecords(GetChangeRecordsRequest request);

}
