package com.barclays.bmg.dao.USSDAuthentication;

import com.barclays.bmg.service.request.USSDAuthentication.GetChangeRecordsRequest;
import com.barclays.bmg.service.response.USSDAuthentication.AuthenticateChangeServiceResponse;

public interface AuthenticateChangeRequestDao {

	public AuthenticateChangeServiceResponse retrieveChangeRecords(GetChangeRecordsRequest request);

}
