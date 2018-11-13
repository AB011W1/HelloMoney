package com.barclays.ussd.bmg.selfregister.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class SelfRegisterInitRequest implements BmgBaseRequestBuilder {

    @SuppressWarnings("unchecked")
    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();

	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	String userBranchSelection = userInputMap.get(USSDInputParamsEnum.SELFREG_BRANCH.getParamName());
	if (userBranchSelection != null && StringUtils.isNotEmpty(userBranchSelection)) {
	    List<UssdBranchLookUpDTO> branchList = (List<UssdBranchLookUpDTO>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		    USSDInputParamsEnum.SELFREG_BRANCH.getTranId());
	    //Forgot Pin Change
	    if(branchList==null){
	    	branchList = (List<UssdBranchLookUpDTO>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
	    		    USSDInputParamsEnum.FORGOT_PIN_BRANCH.getTranId());

	    }
	    UssdBranchLookUpDTO branchCodeLookUpDTO = branchList.get(Integer.parseInt(userBranchSelection) - 1);
	    requestParamMap.put(USSDInputParamsEnum.SELFREG_BRANCH.getParamName(), branchCodeLookUpDTO.getBranchCode());
	}
	requestParamMap.put(USSDInputParamsEnum.SELFREG_MOBILE.getParamName(), requestBuilderParamsDTO.getMsisdnNo());
	requestParamMap.put(USSDInputParamsEnum.SELFREG_ACCOUNT.getParamName(), userInputMap.get(USSDInputParamsEnum.SELFREG_ACCOUNT.getParamName()));
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	ussdBaseRequest.setRequestParamMap(requestParamMap);
	return ussdBaseRequest;
    }

}
