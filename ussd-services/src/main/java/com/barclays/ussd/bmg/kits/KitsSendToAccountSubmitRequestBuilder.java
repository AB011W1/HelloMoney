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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.TransactionAmt;

public class KitsSendToAccountSubmitRequestBuilder implements BmgBaseRequestBuilder {

	@SuppressWarnings("unchecked")
    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {

		USSDBaseRequest request = new USSDBaseRequest();
		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		Map<String, Object> txSessions = requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();

		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		//Receiver's Account Number
		requestParamMap.put("receipientAccountNo", userInputMap.get(USSDInputParamsEnum.KITS_STA_ACCOUNT_NUMBER.getParamName()));
		//Receiver's Bank Details
		requestParamMap.put("selectedBank",(String)txSessions.get("SelectedBank"));
		requestParamMap.put("selectedBankSortCode",(String)txSessions.get("SelectedBankSortCode"));
		//amount entered
		requestParamMap.put("txnAmount", userInputMap.get(USSDInputParamsEnum.KITS_STA_AMOUNT.getParamName()));
		//reason entered
		requestParamMap.put("txnReason", userInputMap.get(USSDInputParamsEnum.KITS_STA_REASON.getParamName()));
		//debitAccount

		 List<AccountDetails> accList= (List<AccountDetails>) txSessions.get(USSDInputParamsEnum.KITS_STA_ACCOUNT_NUM.getTranId());
        int selectedAccSeq=Integer.parseInt(userInputMap.get(USSDInputParamsEnum.KITS_STA_ACCOUNT_NUM.getParamName()))-1;
        AccountDetails acc=accList.get(selectedAccSeq);
        requestParamMap.put("debitAccount", acc.getActNo());
//
//
//		List<CustomerMobileRegAcct> accList=new ArrayList<CustomerMobileRegAcct>();
//		accList= (List<CustomerMobileRegAcct>) txSessions.get(USSDInputParamsEnum.KITS_STA_ACCOUNT_NUM.getTranId());
//        int selectedAccSeq=Integer.parseInt(userInputMap.get(USSDInputParamsEnum.KITS_STA_ACCOUNT_NUM.getParamName()))-1;
//        CustomerMobileRegAcct acc=accList.get(selectedAccSeq);
        requestParamMap.put("debitAccount", acc.getActNo());
        //Activity Id
		requestParamMap.put("activityId", "KITS_PTA_BILLPAY");

		//Set the fields for MakeBillPaymentRequest - CPB 09/05
		String cpbflag = (String)txSessions.get("CpbMakeBillPaymentFields");
		if(cpbflag !=null && cpbflag.equals("CpbMakeBillPaymentFields")){
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
		request.setRequestParamMap(requestParamMap);
		return request;

    }
}
