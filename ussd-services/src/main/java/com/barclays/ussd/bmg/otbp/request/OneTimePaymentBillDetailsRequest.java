package com.barclays.ussd.bmg.otbp.request;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;

public class OneTimePaymentBillDetailsRequest implements BmgBaseRequestBuilder {

	@Override
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {

		//USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		/*
		 * Map<String, String> userInputMap = ussdSessionMgmt
		 * .getUserTransactionDetails().getUserInputMap();
		 *
		 * Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
		 */
		// TODO add a BMG call for fetching bill details
		return new USSDBaseRequest();
	}

}
