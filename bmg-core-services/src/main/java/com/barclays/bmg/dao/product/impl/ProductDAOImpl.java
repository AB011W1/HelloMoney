package com.barclays.bmg.dao.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.dao.impl.BaseDAOImpl;
import com.barclays.bmg.dao.product.ProductDAO;
import com.barclays.bmg.dto.ProductDTO;
import com.barclays.bmg.service.product.request.ProductServiceRequest;
import com.barclays.bmg.service.product.response.ProductListServiceResponse;
import com.barclays.bmg.service.product.response.ProductServiceResponse;

public class ProductDAOImpl extends BaseDAOImpl implements ProductDAO {

    private static final String BUSINESS_ID = "businessId";
    private static final String PRODUCT_GROUP = "productGroup";
    private static final String PRODUCT_CODE = "productCode";
    private static final String PRODUCT_CODE_LIST = "productCodeList";

    private static final String LOAD_PRODUCT_LIST = "getProductList";
    private static final String LOAD_PRODUCTS = "getProducts";
    private static final String LOAD_PRODUCT_CODE_DESC = "getProductListByProductCodes";

    public ProductListServiceResponse getProductsByProductGroup(ProductServiceRequest request) {

	Map parameterMap = new HashMap();

	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(PRODUCT_GROUP, request.getProductGroup());

	List<ProductDTO> productList = this.queryForList(LOAD_PRODUCT_LIST, parameterMap);

	ProductListServiceResponse productServiceResponse = new ProductListServiceResponse();
	productServiceResponse.setProductList(productList);

	return productServiceResponse;
    }

    @Override
    public ProductListServiceResponse getProductsByProductCode(ProductServiceRequest request) {

	Map parameterMap = new HashMap();

	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(PRODUCT_CODE, request.getProductCode());

	ProductListServiceResponse productServiceResponse = new ProductListServiceResponse();

	List<ProductDTO> productList = this.queryForList(LOAD_PRODUCTS, parameterMap);

	productServiceResponse.setProductList(productList);

	return productServiceResponse;
    }

    @Override
    public ProductListServiceResponse getProductDescByProductCodes(ProductServiceRequest request) {
	Map parameterMap = new HashMap();

	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(PRODUCT_CODE_LIST, request.getProdCodeList());

	ProductListServiceResponse productServiceResponse = new ProductListServiceResponse();

	List<ProductDTO> productList = this.queryForList(LOAD_PRODUCT_CODE_DESC, parameterMap);

	Map<String, String> prodCodeDesc = new HashMap<String, String>();
	if (productList != null) {
	    for (ProductDTO prod : productList) {
		prodCodeDesc.put(prod.getProductCode(), prod.getProductDesc());
	    }
	}
	productServiceResponse.setProdCodeDescMap(prodCodeDesc);

	return productServiceResponse;
    }

    /**
     * Retrieve product by product code and currency
     * 
     * @param request
     * @return
     */
    @Override
    public ProductServiceResponse getProductsByCurrencyCode(ProductServiceRequest request) {

	Map<String, String> parameterMap = new HashMap<String, String>();

	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(PRODUCT_CODE, request.getProductCode());
	parameterMap.put("currencyCode", request.getCurrencyCode());

	ProductServiceResponse productServiceResponse = new ProductServiceResponse();

	ProductDTO product = (ProductDTO) this.queryForObject(LOAD_PRODUCTS, parameterMap);

	productServiceResponse.setProduct(product);

	return productServiceResponse;
    }

}
