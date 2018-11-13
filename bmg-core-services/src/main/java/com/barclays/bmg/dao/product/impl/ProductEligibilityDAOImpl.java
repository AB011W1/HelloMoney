package com.barclays.bmg.dao.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.dao.impl.BaseDAOImpl;
import com.barclays.bmg.dao.product.ProductEligibilityDAO;
import com.barclays.bmg.dto.ProductEligibilityDTO;
import com.barclays.bmg.service.product.request.ProductEligibilityServiceRequest;
import com.barclays.bmg.service.product.response.ProductEligibilityListServiceResponse;

public class ProductEligibilityDAOImpl extends BaseDAOImpl implements ProductEligibilityDAO {

    private static final String ACTIVITY_ID = "activityId";
    private static final String SYSTEM_ID = "systemId";
    private static final String BUSINESS_ID = "businessId";
    private static final String PRODUCT_INDICATOR = "productIndicator";
    private static final String INCOREXC = "incOrExc";
    private static final String PRODUCT_CODE = "productCode";
    private static final String PRODUCT_CATEGORY = "productCategory";
    private static final String DR_CR_INDICATOR = "drOrCr";
    private static final String ROLE_CATEGARY_CODE = "roleCategoryCode";

    private static final String LOAD_PRODUCT_ELIGIBILITY = "loadProductEligibility";
    private static final String LOAD_PRODUCT_ELIGIBILITY_BY_INDICATOR = "loadProductEligibilityByIndicator";
    private static final String LOAD_PRODUCT_ELIGIBILITY_BY_INCOREXC = "loadRequiredProductEligibilityByIncOrExc";

    @SuppressWarnings("unchecked")
    public ProductEligibilityListServiceResponse getProductEligibilityByActivityId(ProductEligibilityServiceRequest request) {

	Map<String, String> paramMap = new HashMap<String, String>();

	paramMap.put(SYSTEM_ID, request.getContext().getSystemId());
	paramMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	paramMap.put(ACTIVITY_ID, request.getActivityId());
	paramMap.put(DR_CR_INDICATOR, request.getDrOrCr());
	paramMap.put(ROLE_CATEGARY_CODE, request.getRoleCategoryCode());
	// paramMap.put(PRODUCT_INDICATOR, productIndicator);

	List<ProductEligibilityDTO> productEligibilityDTOList = this.queryForList(LOAD_PRODUCT_ELIGIBILITY, paramMap);

	ProductEligibilityListServiceResponse productEligibilityListServiceResponse = new ProductEligibilityListServiceResponse();
	productEligibilityListServiceResponse.setProductEligibilityList(productEligibilityDTOList);

	return productEligibilityListServiceResponse;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ProductEligibilityListServiceResponse getProductEligibilityByProductIndicator(ProductEligibilityServiceRequest request) {

	Map<String, String> paramMap = new HashMap<String, String>();

	paramMap.put(SYSTEM_ID, request.getContext().getSystemId());
	paramMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	paramMap.put(ACTIVITY_ID, request.getActivityId());
	paramMap.put(PRODUCT_INDICATOR, request.getProductIndicator());

	List<ProductEligibilityDTO> productEligibilityDTOList = this.queryForList(
		LOAD_PRODUCT_ELIGIBILITY_BY_INDICATOR, paramMap);

	ProductEligibilityListServiceResponse productEligibilityListServiceResponse = new ProductEligibilityListServiceResponse();
	productEligibilityListServiceResponse.setProductEligibilityList(productEligibilityDTOList);

	return productEligibilityListServiceResponse;

    }

    @SuppressWarnings("unchecked")
    @Override
    public ProductEligibilityListServiceResponse getProductEligibilityByIncOrExc(ProductEligibilityServiceRequest request) {

	Map<String, String> paramMap = new HashMap<String, String>();

	paramMap.put(SYSTEM_ID, request.getContext().getSystemId());
	paramMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	paramMap.put(ACTIVITY_ID, request.getContext().getActivityId());
	paramMap.put(INCOREXC, request.getIncOrExc());
	if (request.getProductCode() != null) {
	    paramMap.put(PRODUCT_CODE, request.getProductCode());
	}

	paramMap.put(PRODUCT_CATEGORY, request.getProductCategory());

	List<ProductEligibilityDTO> productEligibilityDTOList = this.queryForList(LOAD_PRODUCT_ELIGIBILITY_BY_INCOREXC,
		paramMap);

	ProductEligibilityListServiceResponse productEligibilityListServiceResponse = new ProductEligibilityListServiceResponse();
	productEligibilityListServiceResponse.setProductEligibilityList(productEligibilityDTOList);

	return productEligibilityListServiceResponse;

    }

}
