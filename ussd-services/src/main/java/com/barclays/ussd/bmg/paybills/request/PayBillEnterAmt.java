/**
 * PayBillEnterAmt.java
 */
package com.barclays.ussd.bmg.paybills.request;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.NonBMGRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;

/**
 * @author BTCI This class generates request for entering bill pay amount
 * 
 */
public class PayBillEnterAmt extends NonBMGRequestBuilder {

    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	return request;
    }

}
