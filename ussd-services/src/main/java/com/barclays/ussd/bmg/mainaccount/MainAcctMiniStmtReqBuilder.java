package com.barclays.ussd.bmg.mainaccount;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.ussd.auth.bean.CasaAccount;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.exception.USSDNonBlockingErrorCodes;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class MainAcctMiniStmtReqBuilder implements BmgBaseRequestBuilder {
    private static final String ACCOUNT_STATUS_ACTIVE = "Active";
    private static final String MINI_STATMENT_DUR = "days";
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(MainAcctMiniStmtReqBuilder.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject (com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
     */
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {

	String localCurrency = BMBContextHolder.getContext().getLocalCurrency();

	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>(3);

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	CasaAccount mainAcctObj = ussdSessionMgmt.getUserProfile().getMainAccount();

	if (mainAcctObj != null) {
	    if (StringUtils.equalsIgnoreCase(mainAcctObj.getCurr(), localCurrency)
		    && StringUtils.equalsIgnoreCase(ACCOUNT_STATUS_ACTIVE, mainAcctObj.getAccStatus())) {

		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

		requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().setUserInputMap(new HashMap<String, String>(5));
		Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		userInputMap.put(USSDInputParamsEnum.MINI_STMT_MAIN_ACCT_SEL_AC.getParamName(), mainAcctObj.getAccountNumber());
		userInputMap.put(USSDInputParamsEnum.MINI_STMT_SEL_BR.getParamName(), mainAcctObj.getBranchCode());

		String miniStatementDuration = ConfigurationManager.getString(USSDConstants.MINI_STATEMENT_DURATION,
			USSDConstants.DEFAULT_MINI_STMT_DURATION);
		userInputMap.put(MINI_STATMENT_DUR, miniStatementDuration);

		requestParamMap.putAll(userInputMap);

		request.setRequestParamMap(requestParamMap);
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
	    }
	} else {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Couldn't find a main acctount for the user...");
	    }
	    throw new USSDNonBlockingException(USSDNonBlockingErrorCodes.MAIN_ACCT_NT_FOUND.getUssdErrorCode());
	}

	return request;
    }
}
