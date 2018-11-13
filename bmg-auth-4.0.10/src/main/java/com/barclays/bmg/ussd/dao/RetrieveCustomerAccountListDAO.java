package com.barclays.bmg.ussd.dao;

import com.barclays.bmg.ussd.auth.service.request.RetrieveCustomerAccountListServiceRequest;
import com.barclays.bmg.ussd.auth.service.response.RetrieveCustomerAccountListServiceResponse;

public interface RetrieveCustomerAccountListDAO {

    public RetrieveCustomerAccountListServiceResponse retrieveCustomerAccount(RetrieveCustomerAccountListServiceRequest request);
}
