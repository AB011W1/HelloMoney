package com.barclays.ussd.bmg.fundtransfer.otherbarclaysfundtx.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.TransactionAmt;

public class OBAFTConfirmRequestBuilder implements BmgBaseRequestBuilder {
    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	Map<String, Object> txSessions = requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	requestParamMap.putAll(userInputMap);

	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());

	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	request.setSerVer(USSDConstants.BMG_SERVICE_VERSION_VALUE);
	List<String> txnRefNoStrList = (List<String>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.INT_FT_VALIDATE.getTranId());
	requestParamMap.put(USSDConstants.BMG_TANX_REF_NO, txnRefNoStrList.get(0));
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	request.setRequestParamMap(requestParamMap);

	//Set the fields for MakeDomesticFundTransferRequest - CPB 30/05/2017
	String cpbflag = (String)txSessions.get("CpbMakeDomesticFundTransferFields");
	if(cpbflag !=null && cpbflag.equals("CpbMakeDomesticFundTransferFields")){
		TransactionAmt chargeAmount = new TransactionAmt();
		chargeAmount = (TransactionAmt)txSessions.get("CpbChargeAmount");
		String cpbChargeAmount = chargeAmount.getAmt();
		requestParamMap.put("CpbChargeAmount", String.valueOf(cpbChargeAmount));
		requestParamMap.put("CpbFeeGLAccount", (String)txSessions.get("CpbFeeGLAccount"));
		Double CpbTaxAmount = (Double) txSessions.get("CpbTaxAmount");
		requestParamMap.put("CpbTaxAmount", String.valueOf(CpbTaxAmount));
		requestParamMap.put("CpbTaxGLAccount", (String)txSessions.get("CpbTaxGLAccount"));
		requestParamMap.put("CpbChargeNarration", (String)txSessions.get("CpbChargeNarration"));
		requestParamMap.put("CpbExciseDutyNarration", (String)txSessions.get("CpbExciseDutyNarration"));
		requestParamMap.put("CpbtypeCode", (String)txSessions.get("CpbtypeCode"));
		requestParamMap.put("CpbValue", (String)txSessions.get("CpbValue"));
		requestParamMap.put("CpbMakeDomesticFundTransferFields", "setCpbDomesticInfoFields");
	}else if(cpbflag !=null && cpbflag.equals("xelerateOffline")){
		requestParamMap.put("CpbMakeDomesticFundTransferFields", "xelerateOffline");
		requestParamMap.put("CpbChargeAmount", String.valueOf("0.0"));
		requestParamMap.put("CpbTaxAmount", String.valueOf("0.0"));
	}

	return request;
    }

}
