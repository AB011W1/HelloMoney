/**
 *
 */
package com.barclays.ussd.bmg.otbp.request;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;

/**
 * @author BTCI
 *
 */
public class OneTimeWUCBillPayEnterCustomerNumRefRequest implements BmgBaseRequestBuilder {

	@Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
    	return new USSDBaseRequest();
    }




}
