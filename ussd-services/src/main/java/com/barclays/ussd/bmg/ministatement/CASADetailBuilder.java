/**
 * CASADetailBuilder.java
 */
package com.barclays.ussd.bmg.ministatement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI
 *
 */
public class CASADetailBuilder implements BmgBaseRequestBuilder {

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject (com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
     */
    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>(3);

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();

	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	if (userInputMap == null) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
	}

	List<CustomerMobileRegAcct> lstAccntDet = (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.MINI_STMT_SEL_AC.getTranId());
	String userInput = userInputMap.get(USSDInputParamsEnum.MINI_STMT_SEL_AC.getParamName());
	CustomerMobileRegAcct userSelectedAccount = new CustomerMobileRegAcct();
	if(null != lstAccntDet)
		userSelectedAccount = lstAccntDet.get(Integer.parseInt(userInput) - 1);

	if (lstAccntDet != null && !lstAccntDet.isEmpty() && StringUtils.isNotBlank(userInput)) {
	    request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	    requestParamMap.put(USSDInputParamsEnum.MINI_STMT_SEL_AC.getParamName(), userSelectedAccount.getActNo());
	    requestParamMap.put(USSDInputParamsEnum.MINI_STMT_SEL_BR.getParamName(), userSelectedAccount.getBrnCde());

	    String miniStatementDuration = ConfigurationManager.getString(USSDConstants.MINI_STATEMENT_DURATION,
		    USSDConstants.DEFAULT_MINI_STMT_DURATION);
	    requestParamMap.put(USSDInputParamsEnum.MINI_STMT_RESP.getParamName(), miniStatementDuration);

	    request.setRequestParamMap(requestParamMap);
	}
	return request;
    }

}
