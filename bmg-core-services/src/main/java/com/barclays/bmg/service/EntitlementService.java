package com.barclays.bmg.service;

import com.barclays.bmg.service.product.response.EntitlementServiceResponse;
import com.barclays.bmg.service.request.EntitlementServiceRequest;

public interface EntitlementService {

    public EntitlementServiceResponse retrieveEntitlement(EntitlementServiceRequest request);
}
