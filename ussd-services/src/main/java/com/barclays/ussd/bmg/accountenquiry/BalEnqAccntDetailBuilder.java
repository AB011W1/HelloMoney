/**
 * BalEnqAccntDetailBuilder.java
 */
package com.barclays.ussd.bmg.accountenquiry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI This class builds balance inquiry account detail requests.
 * 
 */
public class BalEnqAccntDetailBuilder implements BmgBaseRequestBuilder {

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject (com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
     */
    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>(3);

	// String userInput = requestBuilderParamsDTO.getUserInput();

	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	if (userInputMap == null) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
	}
	String userInput = userInputMap.get(USSDInputParamsEnum.BAL_ENQ_SEL_AC.getParamName());
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	List<CustomerMobileRegAcct> lstAccntDet = (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.BAL_ENQ_SEL_AC.getTranId());
	if (lstAccntDet != null && !lstAccntDet.isEmpty() && StringUtils.isNotBlank(userInput)) {
	    CustomerMobileRegAcct acntDet = lstAccntDet.get(Integer.parseInt(userInput) - 1);
	    request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	    requestParamMap.put(USSDInputParamsEnum.MINI_STMT_SEL_AC.getParamName(), acntDet.getActNo());
	    request.setRequestParamMap(requestParamMap);
	}
	return request;
    }

}
