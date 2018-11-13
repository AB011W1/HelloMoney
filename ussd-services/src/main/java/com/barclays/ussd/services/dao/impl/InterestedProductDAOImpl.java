package com.barclays.ussd.services.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.barclays.ussd.dto.InterestedProductDTO;
import com.barclays.ussd.dto.InterestedSubProductDTO;

public class InterestedProductDAOImpl extends SqlMapClientDaoSupport implements InterestedProductDAO {

    private static String INTERESTED_PRODUCT_LIST_LOOKUP_QUERY = "ussdProdcutList";
    private static String INTERESTED_SUB_PRODUCT_LIST_LOOKUP_QUERY = "ussdSubProdcutList";

    @Override
    public List<InterestedProductDTO> getProductList(String businessId) {

	InterestedProductDTO interestedProductDTO = new InterestedProductDTO();
	interestedProductDTO.setBusinessId("KEBRB");
	List<InterestedProductDTO> productList = this.getSqlMapClientTemplate().queryForList(INTERESTED_PRODUCT_LIST_LOOKUP_QUERY,
		interestedProductDTO);
	return productList;
    }

    public List<InterestedSubProductDTO> getSubProductList(String businessId, String productName) {

	InterestedSubProductDTO interestedSubProductDTO = new InterestedSubProductDTO();
	interestedSubProductDTO.setProductName(productName);
	interestedSubProductDTO.setBusinessId(businessId);

	List<InterestedSubProductDTO> subProductList = this.getSqlMapClientTemplate().queryForList(INTERESTED_SUB_PRODUCT_LIST_LOOKUP_QUERY,
		interestedSubProductDTO);
	return subProductList;
    }
}
