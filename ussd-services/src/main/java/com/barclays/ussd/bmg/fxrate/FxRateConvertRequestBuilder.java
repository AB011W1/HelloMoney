package com.barclays.ussd.bmg.fxrate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.sysprefs.services.ListValueCacheDTO;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class FxRateConvertRequestBuilder implements BmgBaseRequestBuilder {
    private static final String TRANSACTION_TYPE = "DAC";
    private static final String PRIMARY_INDICATOR = "Y";

    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	Map<String, Object> txSessions = requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	List<ListValueCacheDTO> listValueCacheDTOList = (List<ListValueCacheDTO>) txSessions.get(USSDInputParamsEnum.FX_RATE_GET_CURR.getTranId());
	String userInput = userInputMap.get(USSDInputParamsEnum.FX_RATE_GET_CURR.getParamName());
	String destinationCurrency = listValueCacheDTOList.get(Integer.valueOf(userInput) - 1).getLabel();
	// String sourceCurrency = getSourceCurrency(ussdSessionMgmt.getBusinessId());
	String sourceCurrency = BMBContextHolder.getContext().getLocalCurrency();

	// PUT in session to show in response.
	txSessions.put(USSDInputParamsEnum.FX_RATE_GET_CURR.getParamName(), destinationCurrency);
	txSessions.put(USSDInputParamsEnum.FX_RATE_CONVERT.getParamName(), sourceCurrency);

	CustomerMobileRegAcct primaryAccount = getPrimaryAccount(ussdSessionMgmt);
	if (primaryAccount != null) {
	    requestParamMap.put(USSDInputParamsEnum.FX_RATE_ACCOUNT_NO.getParamName(), primaryAccount.getActNo());
	    requestParamMap.put(USSDInputParamsEnum.FX_RATE_BRANCH_CODE.getParamName(), primaryAccount.getBrnCde());
	}

	// Added to request Param for BEM Processing
	requestParamMap.put(USSDInputParamsEnum.FX_RATE_GET_CURR.getParamName(), destinationCurrency);
	requestParamMap.put(USSDInputParamsEnum.FX_RATE_CONVERT.getParamName(), sourceCurrency);
	requestParamMap.put(USSDInputParamsEnum.FX_RATE_TRANSACTION_TYPE_CODE.getParamName(), TRANSACTION_TYPE);

	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	ussdBaseRequest.setRequestParamMap(requestParamMap);
	return ussdBaseRequest;
    }

    private CustomerMobileRegAcct getPrimaryAccount(USSDSessionManagement ussdSessionMgmt) {

	AuthUserData userAuthObj = (AuthUserData) ussdSessionMgmt.getUserAuthObj();

	List<CustomerMobileRegAcct> custActs = userAuthObj.getPayData().getCustActs();
	for (CustomerMobileRegAcct acct : custActs) {
	    if (PRIMARY_INDICATOR.equalsIgnoreCase(acct.getPriInd())) {
		return acct;
	    }
	}
	return null;
    }

    private String getSourceCurrency(String businessId) {
	String destCurrency = "";
	if (businessId.equalsIgnoreCase(USSDConstants.BUSINESS_ID_KEBRB)) {
	    destCurrency = USSDConstants.KEBRB_DEF_CURR;
	} else if (businessId.equalsIgnoreCase(USSDConstants.BUSINESS_ID_GHBRB)) {
	    destCurrency = USSDConstants.GHBRB_DEF_CURR;
	} else if (businessId.equalsIgnoreCase(USSDConstants.BUSINESS_ID_ZMBRB)) {
	    destCurrency = USSDConstants.ZMBRB_DEF_CURR;
	} else if (businessId.equalsIgnoreCase(USSDConstants.BUSINESS_ID_ZWBRB)) {
	    destCurrency = USSDConstants.ZWBRB_DEF_CURR;
	}
	return destCurrency;
    }
}