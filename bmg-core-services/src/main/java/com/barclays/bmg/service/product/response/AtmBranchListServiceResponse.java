package com.barclays.bmg.service.product.response;

import java.util.List;
import java.util.Map;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.ProductDTO;

public class AtmBranchListServiceResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = -6695690638978503028L;

    private List<ProductDTO> productList;

    private Map<String, String> prodCodeDescMap;

    public List<ProductDTO> getProductList() {
	return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
	this.productList = productList;
    }

    public Map<String, String> getProdCodeDescMap() {
	return prodCodeDescMap;
    }

    public void setProdCodeDescMap(Map<String, String> prodCodeDescMap) {
	this.prodCodeDescMap = prodCodeDescMap;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof AtmBranchListServiceResponse))
	    return false;
	AtmBranchListServiceResponse other = (AtmBranchListServiceResponse) obj;
	return productList.equals(other);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((productList == null) ? 0 : productList.hashCode());
	result += prime * result + ((prodCodeDescMap == null) ? 0 : prodCodeDescMap.hashCode());
	return result;
    }
}
