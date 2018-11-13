/**
 * OthBnkSelReasonRequestBuilder.java
 */
package com.barclays.ussd.bmg.fundtransfer.otherbank.request;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;

/**
 * @author BTCI
 * 
 */
public class OthBnkSelReasonRequestBuilder implements BmgBaseRequestBuilder {

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	return new USSDBaseRequest();
    }
}
