package com.barclays.bmg.ussd.service;

import com.barclays.bmg.ussd.auth.service.request.RetrieveCustomerDetailsServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.RetrieveCustomerDetailsServiceResponse;

public interface RetrieveCustomerDetailsService {
    public RetrieveCustomerDetailsServiceResponse retrieveCustomerDetails(RetrieveCustomerDetailsServiceRequest request);
}
