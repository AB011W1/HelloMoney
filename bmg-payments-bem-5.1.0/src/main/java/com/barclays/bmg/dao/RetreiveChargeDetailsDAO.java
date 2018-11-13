package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.RetreiveChargeDetailsServiceRequest;
import com.barclays.bmg.service.response.RetreiveChargeDetailsServiceResponse;

public interface RetreiveChargeDetailsDAO {

	public RetreiveChargeDetailsServiceResponse retreiveChargeDetails(RetreiveChargeDetailsServiceRequest request);
}
