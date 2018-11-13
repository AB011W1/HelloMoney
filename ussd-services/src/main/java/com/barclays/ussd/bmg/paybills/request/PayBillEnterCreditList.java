/**
 * PayBillEnterAmt.java
 */
package com.barclays.ussd.bmg.paybills.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.bmg.factory.request.NonBMGRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;

/**
 * @author BTCI This class generates request for entering bill pay amount
 *
 */
public class PayBillEnterCreditList implements BmgBaseRequestBuilder {


@SuppressWarnings("unchecked")
public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();

	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), "ACT_CCD_STATEMENT_TRANS");
	requestParamMap.put("crditCardFlg", "crditCardFlg");

	List<Beneficiery> lstBenef = (List<Beneficiery>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
			USSDInputParamsEnum.PAY_BILLS_PAYEE_LIST.getTranId());
		if (!lstBenef.isEmpty()) {
			Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		    Beneficiery bene = lstBenef.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.PAY_BILLS_PAYEE_LIST.getParamName())) - 1);
		    requestParamMap.put(USSDInputParamsEnum.PAY_BILLS_PAYEE_ID.getParamName(), bene.getPayId());
		}


	request.setRequestParamMap(requestParamMap);
	return request;
}
}