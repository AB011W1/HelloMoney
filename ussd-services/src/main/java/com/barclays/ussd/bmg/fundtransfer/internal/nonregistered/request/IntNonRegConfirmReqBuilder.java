package com.barclays.ussd.bmg.fundtransfer.internal.nonregistered.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.TransactionAmt;

public class IntNonRegConfirmReqBuilder implements BmgBaseRequestBuilder {

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	Map<String, Object> txSessions = requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();

	List<String> txnRefNoList = (List<String>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.INT_NR_FT_CONFIRM.getTranId());
	String txnRefNo = "";
	if (txnRefNoList != null && !txnRefNoList.isEmpty()) {
	    txnRefNo = txnRefNoList.get(0);
	}

	//MZBRB Oneoff
	String nibNo = null;
	if(null != requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
			USSDInputParamsEnum.REG_BENF_GET_NIB_NO.getParamName())){
		nibNo = requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
				USSDInputParamsEnum.REG_BENF_GET_NIB_NO.getParamName()).toString();
		requestParamMap.put("NIB", nibNo);
	}	
	
	//ZMBRB,BWBRB,TZBRB Oneoff
	String bankLetter = null;
	if(null != requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE.getParamName()) 
			&& (requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equalsIgnoreCase("ZMBRB") || 
					requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equalsIgnoreCase("BWBRB") || 
					requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equalsIgnoreCase("TZBRB"))) {
		bankLetter = requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
				USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE.getParamName()).toString();
		requestParamMap.put("BANKLETTER", bankLetter);
		
		//Bank code
		List<UssdBranchLookUpDTO> tempBranchList = (List<UssdBranchLookUpDTO>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
    			USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE_LIST.getTranId());
		UssdBranchLookUpDTO bankCodeLookUpDTO = tempBranchList.get(Integer.parseInt(requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get(USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE_LIST
				.getParamName())) - 1);
		
		requestParamMap.put("BANKCODE", bankCodeLookUpDTO.getBankCode());
	}
	
	requestParamMap.put(USSDInputParamsEnum.INT_NR_FT_CONFIRM.getParamName(), txnRefNo);
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());

	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	// Set the fields for MakeDomesticFundTransferRequest - CPB 31/05
	String cpbflag = (String)txSessions.get("CpbMakeDomesticFundFields");
	if(cpbflag !=null && cpbflag.equals("CpbMakeDomesticFundFields")){
		 TransactionAmt chargeAmount = (TransactionAmt)txSessions.get("CpbChargeAmount");
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
		requestParamMap.put("CpbOtherBarcDomesticFundTransfer", "setCpbDomesticInfoFields");
	}else if(cpbflag !=null && cpbflag.equals("xelerateOffline")){
		requestParamMap.put("CpbOtherBarcDomesticFundTransfer", "xelerateOffline");
		requestParamMap.put("CpbChargeAmount", String.valueOf("0.0"));
		requestParamMap.put("CpbTaxAmount", String.valueOf("0.0"));
	}
	request.setRequestParamMap(requestParamMap);

	return request;
    }
}