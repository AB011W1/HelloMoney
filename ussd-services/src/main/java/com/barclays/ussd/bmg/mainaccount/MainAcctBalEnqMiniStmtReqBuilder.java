package com.barclays.ussd.bmg.mainaccount;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.CasaAccount;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDNonBlockingErrorCodes;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class MainAcctBalEnqMiniStmtReqBuilder implements BmgBaseRequestBuilder {
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(MainAcctBalEnqMiniStmtReqBuilder.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject (com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
     */
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>(3);

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	CasaAccount mainAcctObj = ussdSessionMgmt.getUserProfile().getMainAccount();

	if (mainAcctObj != null) {
	    request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	    requestParamMap.put(USSDInputParamsEnum.BAL_ENQ_MAIN_ACCT_SEL_AC.getParamName(), mainAcctObj.getAccountNumber());
	    request.setRequestParamMap(requestParamMap);
	} else {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Couldn't find a main acctount for the user...");
	    }
	    throw new USSDNonBlockingException(USSDNonBlockingErrorCodes.MAIN_ACCT_NT_FOUND.getUssdErrorCode());
	}

	return request;
    }
}
