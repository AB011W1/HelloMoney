package com.barclays.bmg.service.product.impl;

import com.barclays.bmg.dao.product.ProductDAO;
import com.barclays.bmg.service.product.ProductService;
import com.barclays.bmg.service.product.request.ProductServiceRequest;
import com.barclays.bmg.service.product.response.ProductListServiceResponse;

public class ProductServiceImpl implements ProductService {

    private ProductDAO productDAO;

    /*
     * public ProductListServiceResponse getProductsByProductGroup(ProductServiceRequest request){ return null; }
     */

    public ProductListServiceResponse getProductsByProductGroup(ProductServiceRequest request) {

	return productDAO.getProductsByProductGroup(request);
    }

    public ProductListServiceResponse getProductsByProductCode(ProductServiceRequest request) {

	return productDAO.getProductsByProductCode(request);
    }

    public void setProductDAO(ProductDAO productDAO) {
	this.productDAO = productDAO;
    }

}
