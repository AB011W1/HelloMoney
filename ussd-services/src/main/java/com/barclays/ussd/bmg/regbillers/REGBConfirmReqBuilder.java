package com.barclays.ussd.bmg.regbillers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class REGBConfirmReqBuilder implements BmgBaseRequestBuilder {
    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

	List<String> txnRefNoLst = (List<String>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.REG_BILLER_VALIDATE.getTranId());

	Map<String, String> requestParamMap = new HashMap<String, String>(3);
	requestParamMap.put(USSDInputParamsEnum.REG_BILLER_CONFIRM.getParamName(), txnRefNoLst.get(Integer.parseInt(userInputMap
		.get(USSDInputParamsEnum.REG_BILLER_VALIDATE.getParamName())) - 1));

	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	// WUC Biller change - Botswana 21/07/2017
	Map<String, Object> txSessions = requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	String businessId = requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId();
	String wucBillerCategory = (String)txSessions.get("wucBillerCategory");
	if(businessId.equals("BWBRB") && wucBillerCategory != null){
		if(wucBillerCategory.equals("WUC-2")){
			requestParamMap.put("wucBillerRefCategory",wucBillerCategory);
			requestParamMap.put("customerBillerRef1", requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("billerReferenceNum"));

			String strOneTymContractRefNo = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("customerRefNumber");
			if(strOneTymContractRefNo !=null){
				requestParamMap.put("contractBillerRef2", requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("customerRefNumber"));
			}

			String strRegisterContractRefNo = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("contractBillerReferenceNum");
			if(strRegisterContractRefNo !=null){
				requestParamMap.put("contractBillerRef2", requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("contractBillerReferenceNum"));
			}

		}
	}

	request.setRequestParamMap(requestParamMap);
	return request;

    }
}
