/**
 *PayBillPayeeList.java
 */
package com.barclays.ussd.bmg.airtime.request;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;

/**
 * @author BTCI This class builds request for displaying biller list
 * 
 */
public class AirtimeTopUpPayeeListReqBuilder implements BmgBaseRequestBuilder {

    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	return request;
    }

}
