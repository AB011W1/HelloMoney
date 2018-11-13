package com.barclays.ussd.bmg.user.migration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class UserMigrationInitiateRequestBuilder implements BmgBaseRequestBuilder {
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();

	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	String userBranchSelection = userInputMap.get(USSDInputParamsEnum.USER_MIGRATION_BRANCH_LIST.getParamName());
	if (userBranchSelection != null && StringUtils.isNotEmpty(userBranchSelection)) {
	    List<UssdBranchLookUpDTO> branchList = (List<UssdBranchLookUpDTO>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		    .get(USSDInputParamsEnum.USER_MIGRATION_BRANCH_LIST.getTranId());

	    UssdBranchLookUpDTO branchCodeLookUpDTO = branchList.get(Integer.parseInt(userBranchSelection) - 1);
	    requestParamMap.put(USSDInputParamsEnum.USER_MIGRATION_BRANCH_LIST.getParamName(), branchCodeLookUpDTO.getBranchCode());
	}
	requestParamMap.put(USSDInputParamsEnum.USER_MIGRATION_MOBILE.getParamName(), requestBuilderParamsDTO.getMsisdnNo());
	requestParamMap.put(USSDInputParamsEnum.USER_MIGRATION_ACCOUNT_NO.getParamName(),
		userInputMap.get(USSDInputParamsEnum.USER_MIGRATION_ACCOUNT_NO.getParamName()));
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	ussdBaseRequest.setRequestParamMap(requestParamMap);
	return ussdBaseRequest;
    }
}