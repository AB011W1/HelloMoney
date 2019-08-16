package com.barclays.bmg.ussd.service;

import com.barclays.bmg.ussd.auth.service.request.RetrieveCustomerAccountListServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.RetrieveCustomerAccountListServiceResponse;

public interface RetrieveCustomerAccountListService {

    public RetrieveCustomerAccountListServiceResponse retrieveCustomerAccount(RetrieveCustomerAccountListServiceRequest request);

}
