package com.barclays.ussd.bmg.fundtransfer.otherbank.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.BeneData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

public class KEToOthBnkPayeeInfoRequestBuilder implements BmgBaseRequestBuilder {

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<AccountDetails> lstAccntDet = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.KE_EXT_BANK_FT_SEL_FRM_AC.getTranId());
	if (lstAccntDet != null && !lstAccntDet.isEmpty()) {
	    AccountDetails acntDet = lstAccntDet
		    .get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.KE_EXT_BANK_FT_SEL_FRM_AC.getParamName())) - 1);
	    request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	    requestParamMap.put(USSDInputParamsEnum.KE_EXT_BANK_FT_SEL_FRM_AC.getParamName(), acntDet.getActNo());
	    String payeeIdInput = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(
		    USSDInputParamsEnum.KE_EXT_BANK_FT_TO_AC.getParamName());
	    List<BeneData> lstPayee = (List<BeneData>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.KE_EXT_BANK_FT_TO_AC.getTranId());
	    String payeeId = lstPayee.get(Integer.parseInt(payeeIdInput) - 1).getPayId();
	    requestParamMap.put(USSDInputParamsEnum.KE_EXT_BANK_FT_TO_AC.getParamName(), payeeId);

	    // From A/c and To A/c values set in session.
	    ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.KE_EXT_BANK_FT_SEL_FRM_AC.getParamName(), acntDet);
	    ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.KE_EXT_BANK_FT_TO_AC.getParamName(),
		    lstPayee.get(Integer.parseInt(payeeIdInput) - 1));
	    request.setRequestParamMap(requestParamMap);

	}
	return request;
    }
}
