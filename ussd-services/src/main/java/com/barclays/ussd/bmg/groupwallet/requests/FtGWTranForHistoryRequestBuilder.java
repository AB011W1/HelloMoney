package com.barclays.ussd.bmg.groupwallet.requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.AccountAdditionalInfo;

public class FtGWTranForHistoryRequestBuilder implements BmgBaseRequestBuilder {

	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {
		// TODO Auto-generated method stub
		USSDBaseRequest request = new USSDBaseRequest();
		Map<String, String> requestParamMap = new HashMap<String, String>(3);
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		requestParamMap.put(USSDInputParamsEnum.VLPB_BILLER_LST.getParamName(), USSDConstants.BILL_PAY);
		requestParamMap.put("groupWalletFlow","true");

		requestParamMap.put("fundsTransferType","IT");
		USSDSessionManagement ussdSessionMgmt= requestBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, String> userInputMap = ussdSessionMgmt
		.getUserTransactionDetails().getUserInputMap();
		String userInput = userInputMap.get("accNo");
		List<AccountAdditionalInfo> srcLst = (List<AccountAdditionalInfo>) ussdSessionMgmt
		.getCustaccountList();
		AccountAdditionalInfo selectedFrmAcct = srcLst.get(Integer
				.parseInt(userInput) - 1);
		requestParamMap.put("debitAccountNumber", selectedFrmAcct
		.getAccountAdditionalInfo().getAccountId().trim());
		request.setRequestParamMap(requestParamMap);

		return request;
	}

}
