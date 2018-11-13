package com.barclays.bmg.service.product.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.ProductDTO;

public class ProductServiceResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = 3808912318145225104L;
    // private ProductDTO product;
    private ProductDTO product;

    public ProductDTO getProduct() {
	return product;
    }

    public void setProduct(ProductDTO product) {
	this.product = product;
    }

}
