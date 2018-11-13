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
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

/**
 * @author BTCI This class builds balance inquiry account detail requests.
 * 
 */
public class BalanceEnquiryDetailBuilder implements BmgBaseRequestBuilder {

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject (com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
     */

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>(3);

	String userInput = requestBuilderParamsDTO.getUserInput();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	List<AccountDetails> lstAccntDet = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.BAL_ENQ_SEL_AC.getTranId());
	if (lstAccntDet != null && !lstAccntDet.isEmpty() && StringUtils.isNotBlank(userInput)) {
	    AccountDetails acntDet = lstAccntDet.get(Integer.parseInt(userInput) - 1);
	    request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	    requestParamMap.put(USSDInputParamsEnum.BAL_ENQ_SEL_AC.getParamName(), acntDet.getActNo());
	    request.setRequestParamMap(requestParamMap);
	}
	return request;
    }

}
