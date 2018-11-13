/**
 * PayBillConfirm.java
 */
package com.barclays.ussd.bmg.paybills.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.TransactionAmt;

/**
 * @author BTCI This class displays final confirmation screen
 *
 */
public class PayBillConfirm implements BmgBaseRequestBuilder {

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, Object> txSessions = requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();

	List<String> txnRefNoLst = (List<String>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.PAY_BILLS_SUBMIT.getTranId());

	Map<String, String> requestParamMap = new HashMap<String, String>();
	requestParamMap.put(USSDInputParamsEnum.PAY_BILLS_CONFIRM.getParamName(), txnRefNoLst.get(Integer.parseInt(requestBuilderParamsDTO
		.getUserInput()) - 1));

	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	request.setRequestParamMap(requestParamMap);

	//Set the fields for MakeBillPaymentRequest - CPB 26/05
	String cpbflag = (String)txSessions.get("CpbMakeBillPaymentFields");
	if(cpbflag !=null && cpbflag.equals("CpbMakeBillPaymentFields")){
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
		requestParamMap.put("CpbMakeBillPaymentFields", "setCpbFields");
	}else if(cpbflag !=null && cpbflag.equals("xelerateOffline")){
		requestParamMap.put("CpbMakeBillPaymentFields", "xelerateOffline");
		requestParamMap.put("CpbChargeAmount", String.valueOf("0.0"));
		requestParamMap.put("CpbTaxAmount", String.valueOf("0.0"));
	}

	return request;
    }

}
