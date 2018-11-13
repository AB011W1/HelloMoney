/**
 * 
 */
package com.barclays.ussd.bmg.otbp.request;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDInputParamsEnum;

/**
 * @author BTCI
 * 
 */
public class OneTimeBillPayBillNumRequest implements BmgBaseRequestBuilder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject
	 * (com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
	 */
	@Override
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

		if (userInputMap == null) {
			userInputMap = new HashMap<String, String>();
			requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().setUserInputMap(userInputMap);
		}

		userInputMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ENTER_AMT.getTranId(), requestBuilderParamsDTO
				.getUserInput());

		return new USSDBaseRequest();
	}

}
