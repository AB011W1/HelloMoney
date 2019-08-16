package com.barclays.bmg.ussd.dao;

import com.barclays.bmg.ussd.auth.service.request.RetrieveCustomerDetailsServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.RetrieveCustomerDetailsServiceResponse;

public interface RetrieveCustomerDetailsDAO {
    public RetrieveCustomerDetailsServiceResponse retrieveCustomerDetails(RetrieveCustomerDetailsServiceRequest request);
}
