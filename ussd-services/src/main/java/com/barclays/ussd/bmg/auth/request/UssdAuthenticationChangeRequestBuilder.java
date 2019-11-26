package com.barclays.ussd.bmg.auth.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.service.response.USSDAuthentication.GetRecordResDocumentInfo;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

/**
 * This Class is used to set the required parameters for the update records
 * request
 *
 */
public class UssdAuthenticationChangeRequestBuilder implements BmgBaseRequestBuilder {

	@SuppressWarnings("unchecked")
	@Override
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {

		USSDBaseRequest request = new USSDBaseRequest();
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, String> requestParamMap = new HashMap<String, String>();

		// Get documment list stored in session
		List<GetRecordResDocumentInfo> updateRecordList = (List<GetRecordResDocumentInfo>) ussdSessionMgmt
				.getTxSessions().get(USSDConstants.DOCUMENTS);

		if (updateRecordList != null) {
			request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
			request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
			requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
			requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
			requestParamMap.put(USSDInputParamsEnum.AUTHORIZE_CHANGE_REQUEST.getParamName(),
					requestBuilderParamsDTO.getUserInput());
			requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(),
					RequestConstants.UPDATE_CUSTOMER_REQUEST_ACTIVITY_ID);
			requestParamMap.put(USSDConstants.UPDATE_RECORD_LIST, updateRecordList.toString());
			request.setRequestParamMap(requestParamMap);
		}

		return request;
	}

}
