/**
 * PayBillEnterAmt.java
 */
package com.barclays.ussd.bmg.paybills.request;

import java.util.HashMap;
import java.util.Map;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.bmg.factory.request.NonBMGRequestBuilder;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;

import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

/**
 * @author BTCI This class generates request for entering bill pay amount
 *
 */
public class PayBillEnterCreditORCasa extends NonBMGRequestBuilder  {

    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
    	USSDBaseRequest request = new USSDBaseRequest();
    	return null;
        }


}
