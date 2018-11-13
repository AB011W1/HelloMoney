/**
 * OthBnkEnterAmount.java
 */
package com.barclays.ussd.bmg.langpref;

import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDInputParamsEnum;

/**
 * @author BTCI
 * 
 */
public class GetLanguagesValidateReqBuilder implements BmgBaseRequestBuilder {

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {

	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	List<String> langList = (List<String>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.LANG_PREF_GET_LANG_LIST.getTranId());
	String locale = langList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.LANG_PREF_GET_LANG_LIST.getParamName())) - 1);
	requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().put(
		USSDInputParamsEnum.LANG_PREF_GET_LANG_LIST.getParamName(), locale);

	return request;
    }

}
