/**
 * PayBillConfirm.java
 */
package com.barclays.ussd.bmg.registerbenf.external;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

/**
 * @author BTCI This class displays final confirmation screen
 *
 */
public class RegisterBenfExtConfirmReqBuilder implements BmgBaseRequestBuilder {

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();

	String transNodeId=requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
	Map<String, String> requestParamMap = new HashMap<String, String>();
    if(transNodeId.equals("ussd0.4.3.4.2")){
	List<String> txnRefNoLst = (List<String>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.REG_BENF_BENF_CONFIRM.getTranId());
	requestParamMap.put(USSDInputParamsEnum.REG_BENF_BENF_SUBMIT.getParamName(), txnRefNoLst.get(0));
    }else{
    	List<String> txnRefNoLst = (List<String>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
    			USSDInputParamsEnum.REG_BEN_INT_VALIDATE.getTranId());
   		requestParamMap.put(USSDInputParamsEnum.REG_BEN_INT_CONFIRM.getParamName(), txnRefNoLst.get(0));
    }
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	request.setRequestParamMap(requestParamMap);
	return request;
    }

}
