package com.barclays.bmg.service.product;

import com.barclays.bmg.service.product.request.ProductServiceRequest;
import com.barclays.bmg.service.product.response.ProductListServiceResponse;

public interface ProductService {

    /* public ProductListServiceResponse getProductsByProductGroup(ProductServiceRequest request); */

    public ProductListServiceResponse getProductsByProductGroup(ProductServiceRequest request);

    public ProductListServiceResponse getProductsByProductCode(ProductServiceRequest request);

}
