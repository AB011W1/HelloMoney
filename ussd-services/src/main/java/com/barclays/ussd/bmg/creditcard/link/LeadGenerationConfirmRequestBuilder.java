package com.barclays.ussd.bmg.creditcard.link;

import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class LeadGenerationConfirmRequestBuilder implements BmgBaseRequestBuilder {
	/*
	 * (non-Javadoc)
	 *
	 * @see com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject(com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
	 */
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {


		USSDBaseRequest request = new USSDBaseRequest();
		String productName="";
		String subProductName="";
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		if (userInputMap == null) {
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
		}
		List<String> leadSubProdList = (List<String>) ussdSessionMgmt.getTxSessions().get(
				USSDInputParamsEnum.LEAD_GENERATION_PROD_SUB_LST.getTranId());
		productName=userInputMap.get(USSDConstants.LEAD_GEN_PRODUCT_NAME);
		if (leadSubProdList != null) {
			subProductName=leadSubProdList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.LEAD_GENERATION_PROD_SUB_LST
					.getParamName())) - 1);
			requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put("prodSubProdNameMap", null);
		}

		userInputMap.put(USSDConstants.LEAD_GEN_PRODUCT_NAME,productName);
		userInputMap.put(USSDConstants.LEAD_GEN_SUB_PRODUCT_NAME,subProductName);
		request.setRequestParamMap(userInputMap);
		return request;

	}


}
