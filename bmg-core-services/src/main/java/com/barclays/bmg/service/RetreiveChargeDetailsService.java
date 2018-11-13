package com.barclays.bmg.service;

import com.barclays.bmg.service.request.RetreiveChargeDetailsServiceRequest;
import com.barclays.bmg.service.response.RetreiveChargeDetailsServiceResponse;

public interface RetreiveChargeDetailsService {

    public RetreiveChargeDetailsServiceResponse retreiveChargeDetails(RetreiveChargeDetailsServiceRequest request);
}
