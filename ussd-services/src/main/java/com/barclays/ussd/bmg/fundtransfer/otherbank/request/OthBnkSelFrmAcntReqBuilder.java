/**
 * OthBnkSelFrmAcntReqBuilder.java
 */
package com.barclays.ussd.bmg.fundtransfer.otherbank.request;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;

/**
 * @author BTCI
 * 
 */
public class OthBnkSelFrmAcntReqBuilder implements BmgBaseRequestBuilder {

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {

	return new USSDBaseRequest();
    }

}
