package com.barclays.bmg.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.barclays.bmg.dao.EntitlementDAO;
import com.barclays.bmg.dto.EntitlementDTO;
import com.barclays.bmg.service.product.response.EntitlementServiceResponse;
import com.barclays.bmg.service.request.EntitlementServiceRequest;

public class EntitlementDAOImpl extends BaseDAOImpl implements EntitlementDAO {

    private static final String BUSINESS_ID = "businessId";
    private static final String SYSTEM_ID = "systemId";
    private static final String ACTIVITY_ID = "activityId";
    private static final String RETRIEVE_ENTITLEMENT = "retrieveEntitlement";

    @Override
    public EntitlementServiceResponse retrieveEntitlement(EntitlementServiceRequest request) {

	Map<String, String> parameterMap = new HashMap<String, String>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(ACTIVITY_ID, request.getContext().getActivityId());
	EntitlementDTO entitlementDTO = (EntitlementDTO) this.queryForObject(RETRIEVE_ENTITLEMENT, parameterMap);
	EntitlementServiceResponse entitlementServiceResponse = new EntitlementServiceResponse();
	entitlementServiceResponse.setEntitlementDTO(entitlementDTO);
	return entitlementServiceResponse;
    }

}
