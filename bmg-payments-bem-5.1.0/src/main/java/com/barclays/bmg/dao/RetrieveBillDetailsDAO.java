package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.RetrieveBillDetailsServiceRequest;
import com.barclays.bmg.service.response.RetrieveBillDetailsServiceResponse;

public interface RetrieveBillDetailsDAO {
	public RetrieveBillDetailsServiceResponse retreiveBillDetails(RetrieveBillDetailsServiceRequest request);
}
