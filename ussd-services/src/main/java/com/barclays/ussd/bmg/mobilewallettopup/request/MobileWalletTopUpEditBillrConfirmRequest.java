/**
 * DelBillrConfirmRequest.java
 */
package com.barclays.ussd.bmg.mobilewallettopup.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;

/**
 * @author BTCI
 *
 */
public class MobileWalletTopUpEditBillrConfirmRequest implements BmgBaseRequestBuilder {

	 private static final String PAY_GRP = "payGrp";
	 private static final String IS_EDIT_FLOW = "isEditFlow";
	    @SuppressWarnings("unchecked")
	    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
		USSDBaseRequest request = new USSDBaseRequest();
		Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

		List<String> txnRefNoLst = (List<String>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
			USSDInputParamsEnum.MOBILE_WALLET_EDIT_BEN_VALIDATE.getTranId());

		Map<String, String> requestParamMap = new HashMap<String, String>(3);
		requestParamMap.put(USSDInputParamsEnum.MOBILE_WALLET_EDIT_BILLER_CONFIRM.getParamName(), txnRefNoLst.get(Integer.parseInt(userInputMap
			.get(USSDInputParamsEnum.MOBILE_WALLET_EDIT_BEN_VALIDATE.getParamName())) - 1));

		String payeeId = "";
		if (requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() != null
			&& requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.MOBILE_WALLET_PAYEE_LIST.getTranId()) != null) {
		    List<Beneficiery> lstBenef = (List<Beneficiery>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
			    USSDInputParamsEnum.MOBILE_WALLET_PAYEE_LIST.getTranId());
		    if (!lstBenef.isEmpty()) {
			Beneficiery bene = lstBenef.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_PAYEE_LIST.getParamName())) - 1);
			payeeId = bene.getPayId();
		    }
		}
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		requestParamMap.put(PAY_GRP, "WT");
		requestParamMap.put(IS_EDIT_FLOW,"true");
		requestParamMap.put(USSDInputParamsEnum.DEL_BILLER_CONFIRM.getParamName(), payeeId);
		request.setRequestParamMap(requestParamMap);
		return request;

	    }

}
