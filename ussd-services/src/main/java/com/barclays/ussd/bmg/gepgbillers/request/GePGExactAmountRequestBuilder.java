package com.barclays.ussd.bmg.gepgbillers.request;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;

/**
 *
 * @author G01156975
 * The Class builds the request object to be shared with BMG.
 *
 */
public class GePGExactAmountRequestBuilder implements
		BmgBaseRequestBuilder {

	/**
	 * @param requestBuilderParamsDTO
     * @return USSDBaseRequest
	 */
	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {
		return null;
	}

}
