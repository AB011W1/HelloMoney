/**
 * OthBnkEnterAmount.java
 */
package com.barclays.ussd.bmg.langpref;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

/**
 * @author BTCI
 * 
 */
public class GetLanguagesConfirmReqBuilder implements BmgBaseRequestBuilder {

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();

	/*
	 * List<String> txnRefNoLst = (List<String>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
	 * USSDInputParamsEnum.LANG_PREF_CONFIRM.getTranId());
	 */

	Map<String, String> requestParamMap = new HashMap<String, String>();
	/*
	 * requestParamMap.put(USSDInputParamsEnum.LANG_PREF_CONFIRM.getParamName(), txnRefNoLst.get(Integer.parseInt(requestBuilderParamsDTO
	 * .getUserInput()) - 1));
	 */

	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	String selectedLocale = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get(
		USSDInputParamsEnum.LANG_PREF_GET_LANG_LIST.getParamName());
	requestParamMap.put(USSDInputParamsEnum.LANG_PREF_GET_LANG_LIST.getParamName(), selectedLocale.split(USSDConstants.UNDERSCORE_SEPERATOR)[0]);

	request.setRequestParamMap(requestParamMap);
	return request;
    }
}
