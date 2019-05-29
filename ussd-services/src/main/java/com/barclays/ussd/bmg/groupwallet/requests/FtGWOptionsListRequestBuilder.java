package com.barclays.ussd.bmg.groupwallet.requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

public class FtGWOptionsListRequestBuilder implements BmgBaseRequestBuilder {
	@Autowired
    private SystemParameterService systemParameterService;

	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		USSDSessionManagement ussdSessionMgmt=requestBuilderParamsDTO.getUssdSessionMgmt();
		String business_id=ussdSessionMgmt.getBusinessId();
		AuthUserData adata=(AuthUserData)ussdSessionMgmt.getUserAuthObj();
		String groupWalletIndicator = "N",enabledStatus="N";

		SystemParameterServiceRequest sysrequest=new SystemParameterServiceRequest();
		SystemParameterDTO dto=new SystemParameterDTO();
		dto.setBusinessId(business_id);
		dto.setActivityId("COMMON");
		dto.setParameterId("groupWalletEnabled");
		dto.setSystemId("UB");
		sysrequest.setSystemParameterDTO(dto);
		SystemParameterServiceResponse response= systemParameterService.getSystemParameter(sysrequest);
		enabledStatus=response.getSystemParameterDTO().getParameterValue();

		List<CustomerMobileRegAcct> accList=adata.getPayData().getCustActs();
		for(CustomerMobileRegAcct acc:accList)
			if(acc.getGroupWalletIndicator().equals("Y")){
				groupWalletIndicator="Y";
				break;
			}
		if (groupWalletIndicator.equals("N")||enabledStatus.equals("N")) {
		    throw new USSDNonBlockingException(USSDExceptions.USSD_SERVICE_DISABLED.getBmgCode());
		}

		USSDBaseRequest request = new USSDBaseRequest();
    	Map<String, String> requestParamMap = new HashMap<String, String>();
    	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
    	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), "PMT_MW_GW");
    	request.setRequestParamMap(requestParamMap);
    	return request;
	}

}
