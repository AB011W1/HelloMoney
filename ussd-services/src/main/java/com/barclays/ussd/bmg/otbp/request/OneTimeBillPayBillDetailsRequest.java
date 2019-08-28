package com.barclays.ussd.bmg.otbp.request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.TransactionAmt;

public class OneTimeBillPayBillDetailsRequest implements BmgBaseRequestBuilder {

	@SuppressWarnings("unchecked")
	@Override
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {

		String businessId = requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId();
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
		Map<String, Object> txSessions = requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

		String txnRefNo = (String) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
				USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getParamName());
		requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getParamName(), txnRefNo);

		//Set the fields for MakeBillPaymentRequest - CPB 26/05
		String cpbflag = (String)txSessions.get("CpbMakeBillPaymentFields");
		if(cpbflag !=null && cpbflag.equals("CpbMakeBillPaymentFields") && (businessId.equals("KEBRB")|| businessId.equals("UGBRB")|| businessId.equals("GHBRB"))){
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
			requestParamMap.put("CpbMakeBillPaymentFields", "setCpbFields");
		}else if(cpbflag !=null && cpbflag.equals("xelerateOffline")){
			requestParamMap.put("CpbMakeBillPaymentFields", "xelerateOffline");
			requestParamMap.put("CpbChargeAmount", String.valueOf("0.0"));
			requestParamMap.put("CpbTaxAmount", String.valueOf("0.0"));
			requestParamMap.put("CpbMakeBillPaymentFields", "xelerateOffline");
		}

		// WUC Biller change - Botswana 20/07/2017
		String wucBillerCategory = (String)txSessions.get("wucBillerCategory");
		if(businessId.equals("BWBRB") && wucBillerCategory != null){
			if(wucBillerCategory.equals("WUC-2")){
				requestParamMap.put("wucBillerRefCategory",wucBillerCategory);
				requestParamMap.put("customerBillerRef1", ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get("billerRefNumber"));
				requestParamMap.put("contractBillerRef2", ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get("customerRefNumber"));
			}
		}
		if(null!=ussdSessionMgmt.getTxSessions() && null!= ussdSessionMgmt.getTxSessions().get(USSDConstants.PROBASE_INVOICE_DETAILS)
				&& "" != ussdSessionMgmt.getTxSessions().get(USSDConstants.PROBASE_INVOICE_DETAILS)){
			LinkedHashMap<String, String> billDetails = new LinkedHashMap<String, String>();
			billDetails =  (LinkedHashMap<String, String>) ussdSessionMgmt.getTxSessions().get(USSDConstants.PROBASE_INVOICE_DETAILS);
			Iterator it =  billDetails.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				requestParamMap.put(pair.getKey().toString() + "Probase", pair.getValue().toString());
			}
		}
		ussdBaseRequest.setRequestParamMap(requestParamMap);
		return ussdBaseRequest;
	}

}