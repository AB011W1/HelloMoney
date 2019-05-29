package com.barclays.ussd.bmg.groupwallet.requests;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.AccountAdditionalInfo;

public class FtGWtranListRequestBuilder implements BmgBaseRequestBuilder {

	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {
		// TODO Auto-generated method stub
		int userInput=Integer.parseInt(requestBuilderParamsDTO.getUserInput());
		AccountAdditionalInfo accountDetails=(AccountAdditionalInfo)requestBuilderParamsDTO.getUssdSessionMgmt().getCustaccountList().get(userInput-1);
		String accno=accountDetails.getAccountAdditionalInfo().getAccountId();
		String branchCode=accountDetails.getAccountAdditionalInfo().getBranchCode();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		USSDBaseRequest request = new USSDBaseRequest();
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		requestParamMap.put("accNo", accno);
		requestParamMap.put("branchCode", branchCode);
		requestParamMap.put("transactionStatus", "PendingAuthorization");
		requestParamMap.put("actionCode","LIST");
		requestParamMap.put("tranTypeCode","DT");
		request.setRequestParamMap(requestParamMap);

		return request;
	}

}
