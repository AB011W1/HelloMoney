package com.barclays.bmg.dao.product;

import com.barclays.bmg.service.product.request.ProductEligibilityServiceRequest;
import com.barclays.bmg.service.product.response.ProductEligibilityListServiceResponse;

public interface ProductEligibilityDAO {

    public ProductEligibilityListServiceResponse getProductEligibilityByActivityId(ProductEligibilityServiceRequest request);

    public ProductEligibilityListServiceResponse getProductEligibilityByProductIndicator(ProductEligibilityServiceRequest request);

    public ProductEligibilityListServiceResponse getProductEligibilityByIncOrExc(ProductEligibilityServiceRequest request);
}
