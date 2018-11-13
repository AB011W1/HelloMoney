package com.barclays.bmg.dao.intrates.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.constants.SqlMapSystemConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.impl.BaseDAOImpl;
import com.barclays.bmg.dao.intrates.InterestRatesDAO;
import com.barclays.bmg.dto.IntrateDTO;
import com.barclays.bmg.service.intrates.request.InterestRatesServiceRequest;
import com.barclays.bmg.service.intrates.response.InterestRatesServiceResponse;

public class InterestRatesDAOImpl extends BaseDAOImpl implements
		InterestRatesDAO {

	public InterestRatesServiceResponse getIntratesList(
			InterestRatesServiceRequest request) {

		final String BUSINESS_ID 	= 	"businessId";
		final String CATEGORY_CODE	=	"categoryCode";
		final String LOCAL_CURRENCY = "localCurrency";
		
		InterestRatesServiceResponse intRatesServiceResponse = new InterestRatesServiceResponse();

		Context context = request.getContext();

		Map parameterMap = new HashMap();
		
		List<IntrateDTO> intRatesDTOList = new ArrayList<IntrateDTO>();
		parameterMap.put(BUSINESS_ID, context.getBusinessId());
		parameterMap.put(CATEGORY_CODE,request.getCategoryCode());
		if(request.isFCRCountryFlag())
		{
			parameterMap.put(LOCAL_CURRENCY, request.getContext().getLocalCurrency());
			intRatesDTOList =  this.queryForList(
					SqlMapSystemConstants.INTRATE_CACHE_FOR_FCR_COUNTRY, parameterMap);
		}
		else
		{
			intRatesDTOList = this.queryForList(
				SqlMapSystemConstants.INTRATE_CACHE, parameterMap);
		}
		intRatesServiceResponse.setIntrateDTOList(intRatesDTOList);

		return intRatesServiceResponse;

	}

}
