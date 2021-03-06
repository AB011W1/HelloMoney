/**
 *
 */
package com.barclays.ussd.bmg.fd.request;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;

/**
 * @author BTCI
 * 
 */
public class FDViewEnterAmountRequest implements BmgBaseRequestBuilder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject
	 * (com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
	 */
	@Override
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
		USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();

		Map<String, String> requestParamMap = new HashMap<String, String>();
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		ussdBaseRequest.setRequestParamMap(requestParamMap);
		return ussdBaseRequest;
	}

}
