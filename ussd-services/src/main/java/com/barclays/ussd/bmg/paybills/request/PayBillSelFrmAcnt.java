/**
 * PayBillSelFrmAcnt.java
 */
package com.barclays.ussd.bmg.paybills.request;

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
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;

/**
 * @author BTCI This class builds request for Selecting from account
 * 
 */
public class PayBillSelFrmAcnt implements BmgBaseRequestBuilder {

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	// TODO : Move to spring config

	String payeeId = "";
	if (requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() != null
		&& requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.PAY_BILLS_PAYEE_LIST.getTranId()) != null) {
	    List<Beneficiery> lstBenef = (List<Beneficiery>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		    USSDInputParamsEnum.PAY_BILLS_PAYEE_LIST.getTranId());
	    if (!lstBenef.isEmpty()) {
		Beneficiery bene = lstBenef.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.PAY_BILLS_PAYEE_LIST.getParamName())) - 1);
		payeeId = bene.getPayId();
	    }
	}
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	requestParamMap.put(USSDInputParamsEnum.PAY_BILLS_PAYEE_ID.getParamName(), payeeId);
	request.setRequestParamMap(requestParamMap);

	return request;
    }

}
