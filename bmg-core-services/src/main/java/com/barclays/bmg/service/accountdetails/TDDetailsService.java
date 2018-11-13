package com.barclays.bmg.service.accountdetails;

import com.barclays.bmg.service.accountdetails.request.TDDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.TDDetailsServiceResponse;

public interface TDDetailsService {

    public TDDetailsServiceResponse retreiveTDDetails(TDDetailsServiceRequest request);
}
