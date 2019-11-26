package com.barclays.bmg.service.USSDAuthentication;

import com.barclays.bmg.service.request.USSDAuthentication.UpdateRecordsRequest;
import com.barclays.bmg.service.response.USSDAuthentication.AuthenticateChangeServiceResponse;

public interface UssdUpdateRecordsService {

	public AuthenticateChangeServiceResponse updateRecords(UpdateRecordsRequest updateRecordsRequest);

}
