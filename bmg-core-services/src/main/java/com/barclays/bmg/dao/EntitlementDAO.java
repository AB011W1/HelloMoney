package com.barclays.bmg.dao;

import com.barclays.bmg.service.product.response.EntitlementServiceResponse;
import com.barclays.bmg.service.request.EntitlementServiceRequest;

public interface EntitlementDAO {

    public EntitlementServiceResponse retrieveEntitlement(EntitlementServiceRequest request);
}
