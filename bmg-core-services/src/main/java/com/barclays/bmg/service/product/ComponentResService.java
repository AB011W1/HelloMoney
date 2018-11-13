package com.barclays.bmg.service.product;

import com.barclays.bmg.service.product.request.ComponentResServiceRequest;
import com.barclays.bmg.service.product.response.ComponentResServiceResponse;

public interface ComponentResService {

    public ComponentResServiceResponse getComponentResCache(ComponentResServiceRequest request);
}
