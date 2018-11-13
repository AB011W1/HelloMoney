package com.barclays.bmg.dao.product;

import com.barclays.bmg.service.product.request.ProductServiceRequest;
import com.barclays.bmg.service.product.response.ProductListServiceResponse;
import com.barclays.bmg.service.product.response.ProductServiceResponse;

public interface ProductDAO {

    public ProductListServiceResponse getProductsByProductGroup(ProductServiceRequest request);

    public ProductListServiceResponse getProductsByProductCode(ProductServiceRequest request);

    /**
     * Retrieve product by product code and currency
     * 
     * @param request
     * @return
     */
    public ProductServiceResponse getProductsByCurrencyCode(ProductServiceRequest request);

    public ProductListServiceResponse getProductDescByProductCodes(ProductServiceRequest request);
}
