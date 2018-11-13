/**
 * BalEnqAccntDetailBuilder.java
 */
package com.barclays.ussd.bmg.creditcard.link;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI This class builds balance inquiry account detail requests.
 *
 */
public class CreditCardLinkRequestBuilder implements BmgBaseRequestBuilder {

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject (com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
     */
    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	/*USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>(3);

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	List<CustomerMobileRegAcct> lstAccntDet = (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.AT_A_GLANCE_CARD_LIST.getTranId());
	String userSelection = userInputMap.get(USSDInputParamsEnum.AT_A_GLANCE_CARD_LIST.getParamName());
	if (lstAccntDet != null && !lstAccntDet.isEmpty() && StringUtils.isNotBlank(userSelection)) {
	    CustomerMobileRegAcct acntDet = lstAccntDet.get(Integer.parseInt(userSelection) - 1);
	    request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	    requestParamMap.put(USSDInputParamsEnum.AT_A_GLANCE_CARD_LIST.getParamName(), acntDet.getActNo());
	    request.setRequestParamMap(requestParamMap);
	}
	return request;*/
    	return null;
    }

}
