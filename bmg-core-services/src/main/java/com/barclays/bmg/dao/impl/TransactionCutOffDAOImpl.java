package com.barclays.bmg.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.barclays.bmg.dao.TransactionCutOffDAO;
import com.barclays.bmg.dto.TransactionCutOffTimeDTO;
import com.barclays.bmg.service.request.TransactionAbilityRequest;
import com.barclays.bmg.service.response.TransactionAbilityResponse;

public class TransactionCutOffDAOImpl extends BaseDAOImpl implements TransactionCutOffDAO {

    private static final String BUSINESS_ID = "businessId";
    private static final String SYSTEM_ID = "systemId";
    private static final String ACTIVITY_ID = "activityId";
    private static final String GET_TRANSACTION_CUT_OFF_TIME = "getTransactionCutOffTime";

    @Override
    public TransactionAbilityResponse getTransactionCutOffTime(TransactionAbilityRequest request) {
	Map<String, String> parameterMap = new HashMap<String, String>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(ACTIVITY_ID, request.getContext().getActivityId());

	TransactionAbilityResponse response = null;
	TransactionCutOffTimeDTO transactionCutOffTimeDTO = (TransactionCutOffTimeDTO) this
		.queryForObject(GET_TRANSACTION_CUT_OFF_TIME, parameterMap);
	if (transactionCutOffTimeDTO != null) {
	    response = new TransactionAbilityResponse();
	    response.setCutOffTime(transactionCutOffTimeDTO.getCutOffTime());
	}
	return response;
    }

}
