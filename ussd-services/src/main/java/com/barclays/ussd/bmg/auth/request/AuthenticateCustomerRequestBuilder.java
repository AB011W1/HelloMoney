package com.barclays.ussd.bmg.auth.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * request
 *
 */
public class AuthenticateCustomerRequestBuilder implements BmgBaseRequestBuilder {

	@SuppressWarnings("unchecked")
	@Override
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {

		USSDBaseRequest request = new USSDBaseRequest();
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		int selectedAccSeq = Integer.parseInt(userInputMap.get(USSDInputParamsEnum.BAL_ENQ_SEL_AC.getParamName())) - 1;

		List<CustomerMobileRegAcct> lstAccntDet = (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions()
				.get(USSDInputParamsEnum.BAL_ENQ_SEL_AC.getTranId());

		if (lstAccntDet != null && !lstAccntDet.isEmpty()) {
			CustomerMobileRegAcct acntDet = lstAccntDet.get(selectedAccSeq);
			request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
			request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
			requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
			requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
			requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), RequestConstants.AUTH_REQUEST_ACTIVITY_ID);
			requestParamMap.put(USSDConstants.DATA_TYPE_ACCTNO, acntDet.getActNo());
			request.setRequestParamMap(requestParamMap);
		}

		return request;
	}
}