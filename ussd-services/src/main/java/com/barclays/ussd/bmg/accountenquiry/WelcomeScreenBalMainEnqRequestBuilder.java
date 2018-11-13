package com.barclays.ussd.bmg.accountenquiry;

import org.apache.commons.lang.StringUtils;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class WelcomeScreenBalMainEnqRequestBuilder implements BmgBaseRequestBuilder
{
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO)
	{
		if(StringUtils.isNotEmpty(requestBuilderParamsDTO.getUserInput())){
			requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().put(USSDInputParamsEnum.OLAFT_PAYEE_LIST.getParamName(), requestBuilderParamsDTO.getUserInput());
		}
		return null;
	}
}
