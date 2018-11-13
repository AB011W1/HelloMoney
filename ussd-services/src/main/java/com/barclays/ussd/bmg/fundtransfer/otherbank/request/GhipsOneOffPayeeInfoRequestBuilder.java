package com.barclays.ussd.bmg.fundtransfer.otherbank.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

public class GhipsOneOffPayeeInfoRequestBuilder implements BmgBaseRequestBuilder {

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
	if (txSessions == null) {
		txSessions = new HashMap<String, Object>(8);
		ussdSessionMgmt.setTxSessions(txSessions);
	}
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<AccountDetails> lstAccntDet = (List<AccountDetails>)txSessions.get(USSDInputParamsEnum.GHIPS_TRANSFER_FROM_ACC_LIST.getTranId());
	String customerName="";
	if(null != txSessions.get(USSDInputParamsEnum.GHIPS_BANK_ACC_NO.getParamName())){
		customerName= String.valueOf(txSessions.get(USSDConstants.GHIPPS_CUSTOMER_NAME));
	}

	List<UssdBranchLookUpDTO> branchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt
	.getTxSessions().get(
			USSDInputParamsEnum.GHIPS_BANK_LIST
			.getTranId());

	String bankName = branchList.get(Integer.parseInt(userInputMap
			.get(USSDInputParamsEnum.GHIPS_BANK_LIST
					.getParamName()))-1).getBankName();


	if (lstAccntDet != null && !lstAccntDet.isEmpty()) {
	    AccountDetails acntDet = lstAccntDet
		    .get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.GHIPS_TRANSFER_FROM_ACC_LIST.getParamName())) - 1);
	    request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	    requestParamMap.put(USSDInputParamsEnum.GHIPS_TRANSFER_FROM_ACC_LIST.getParamName(), acntDet.getActNo());
	    requestParamMap.put(USSDConstants.GHIPPS_CUSTOMER_NAME, customerName);
	    requestParamMap.put("bankName", bankName);
	    //String payeeId = String.valueOf(ussdSessionMgmt.getTxSessions().get(USSDConstants.GHIPPS_CUSTOMER_NAME));
	    requestParamMap.put("payId", String.valueOf(txSessions.get(USSDInputParamsEnum.GHIPS_BANK_ACC_NO.getParamName())));
	    requestParamMap.put("bankCode",String.valueOf(txSessions.get(USSDInputParamsEnum.GHIPS_BANK_LIST.getParamName())));
	    // From A/c and To A/c values set in session.
	    txSessions.put(USSDInputParamsEnum.GHIPS_TRANSFER_FROM_ACC_LIST.getParamName(), acntDet);
	    //ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.GHIPS_BANK_ACC_NO.getParamName(),USSDInputParamsEnum.GHIPS_BANK_ACC_NO.getTranId());
	    request.setRequestParamMap(requestParamMap);

	}
	return request;
    }
}
