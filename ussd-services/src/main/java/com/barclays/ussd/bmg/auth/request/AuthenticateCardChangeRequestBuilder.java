package com.barclays.ussd.bmg.auth.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.RequestConstants;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * This Class is used to set the required parameters in the USSD Authentication
 * request for Credit Card use-case
 *
 */
public class AuthenticateCardChangeRequestBuilder implements BmgBaseRequestBuilder {

	@SuppressWarnings("unchecked")
	@Override
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {

		USSDBaseRequest request = new USSDBaseRequest();
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		String userCreditSelection = userInputMap
				.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ENTER_CARD_LIST.getParamName());

		List<CustomerMobileRegAcct> creditCardList = (List<CustomerMobileRegAcct>) requestBuilderParamsDTO
				.getUssdSessionMgmt().getTxSessions()
				.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ENTER_CARD_LIST.getTranId());

		if (creditCardList != null && !creditCardList.isEmpty() && StringUtils.isNotBlank(userCreditSelection)) {
			CustomerMobileRegAcct creditCard = creditCardList.get(Integer.parseInt(userCreditSelection) - 1);
			request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
			request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
			requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
			requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
			requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), RequestConstants.AUTH_CARD_CHANGE_REQUEST_ACTIVITY_ID);
			requestParamMap.put(USSDConstants.DATA_TYPE_ACCTNO, creditCard.getActNo());
			request.setRequestParamMap(requestParamMap);
		}

		return request;
	}

}
