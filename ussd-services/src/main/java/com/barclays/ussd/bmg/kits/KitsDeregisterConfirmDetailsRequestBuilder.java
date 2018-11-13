package com.barclays.ussd.bmg.kits;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MsisdnDTO;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Account;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.AccountData;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;

public class KitsDeregisterConfirmDetailsRequestBuilder implements BmgBaseRequestBuilder{
	    /*@Autowired
	    UssdResourceBundle ussdResourceBundle;*/

	    @SuppressWarnings("unchecked")
	    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	    	USSDBaseRequest request = new USSDBaseRequest();
			Map<String, String> requestParamMap = new HashMap<String, String>();

			request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
			request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
			requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
			requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
			requestParamMap.put("mobileNo",requestBuilderParamsDTO.getMsisdnNo());
			requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), "KITS_DEREGISTRATION_LOOKUP");
			request.setRequestParamMap(requestParamMap);
			return request;
	    }
}
