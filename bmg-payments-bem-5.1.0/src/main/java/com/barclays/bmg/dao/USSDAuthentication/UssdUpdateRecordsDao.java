package com.barclays.bmg.dao.USSDAuthentication;

import com.barclays.bmg.service.request.USSDAuthentication.UpdateRecordsRequest;
import com.barclays.bmg.service.response.USSDAuthentication.AuthenticateChangeServiceResponse;

public interface UssdUpdateRecordsDao {

	public AuthenticateChangeServiceResponse updateRecords(UpdateRecordsRequest updateRecordsRequest);

}
