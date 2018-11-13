package com.barclays.bmg.service.product;

import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.service.product.request.ProductEligibilityServiceRequest;
import com.barclays.bmg.service.product.response.ProductEligibilityListServiceResponse;

public interface ProductEligibilityService {

    public ProductEligibilityListServiceResponse getProductEligibilityByActivityId(ProductEligibilityServiceRequest request);

    public ProductEligibilityListServiceResponse getProductEligibilityByProductIndicator(ProductEligibilityServiceRequest request);

    public boolean isProductEligible(ProductEligibilityServiceRequest request);

    public ProductEligibilityListServiceResponse getEligibleAccounts(ProductEligibilityServiceRequest request);

    public boolean isAccountEligible(CustomerAccountDTO account, ProductEligibilityServiceRequest request);
}
