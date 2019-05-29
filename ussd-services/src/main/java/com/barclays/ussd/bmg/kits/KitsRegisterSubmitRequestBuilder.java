package com.barclays.ussd.bmg.kits;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class KitsRegisterSubmitRequestBuilder implements BmgBaseRequestBuilder {
	@SuppressWarnings("unchecked")
    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
		USSDBaseRequest request = new USSDBaseRequest();
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		//Mobile Number input
		requestParamMap.put("mobileNo",requestBuilderParamsDTO.getMsisdnNo());

		//Account Number input
		 List<CustomerMobileRegAcct> accList= (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.KITS_REG_ACCOUNT_NUM.getTranId());
        int selectedAccSeq=Integer.parseInt(userInputMap.get(USSDInputParamsEnum.KITS_REG_ACCOUNT_NUM.getParamName()))-1;
        CustomerMobileRegAcct acc=accList.get(selectedAccSeq);
		requestParamMap.put("accountNo",acc.getActNo());

		//Primary flag input
		String primaryFlag=userInputMap.get(USSDInputParamsEnum.KITS_REG_PRIMARY_ACC.getParamName());
    	if("1".equals(primaryFlag))
    	{
    		requestParamMap.put("primaryFlag","true");
    	}else if("2".equals(primaryFlag))
    	{
    		requestParamMap.put("primaryFlag","false");
    	}
    	//Activity Id input
		requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), "KITS_REGISTRATION");
		request.setRequestParamMap(requestParamMap);
		return request;
    }
}
