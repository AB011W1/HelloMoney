package com.barclays.bmg.service;

import com.barclays.bmg.service.request.RetrieveBillDetailsServiceRequest;
import com.barclays.bmg.service.response.RetrieveBillDetailsServiceResponse;

public interface RetrieveBillDetailsService {

    public RetrieveBillDetailsServiceResponse retreiveBillDetails(RetrieveBillDetailsServiceRequest retreivePayeeListServiceRequest);
}
