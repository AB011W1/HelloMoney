package com.barclays.ussd.bmg.fundtransfer.otherbank.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class GhipsRegisterBenefNickNameRequestBuilder implements BmgBaseRequestBuilder {

	private static final String FT_BENF = "fundtxBenf";

	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
	throws USSDNonBlockingException, USSDBlockingException {

		USSDBaseRequest request = new USSDBaseRequest();
		Map<String, String> requestParamMap = new HashMap<String, String>();

		List<UssdBranchLookUpDTO> bankList = (List<UssdBranchLookUpDTO>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		.get(USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_LIST.getTranId());
		Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		if(bankList!=null && bankList.size() != 0){
			UssdBranchLookUpDTO ussdBranchLookUpDTO = bankList
			.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.GHIPS_BANK_LIST.getParamName())) - 1);
			requestParamMap.put(USSDInputParamsEnum.SELFREG_BRANCH.getParamName(), ussdBranchLookUpDTO.getBankCode());
		}
		requestParamMap.put(USSDInputParamsEnum.SELFREG_ACCOUNT.getParamName(), userInputMap.get(USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_ACC_NO.getParamName()));
		requestParamMap.put(USSDInputParamsEnum.SELFREG_MOBILE.getParamName(), requestBuilderParamsDTO.getMsisdnNo());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		requestParamMap.put(USSDInputParamsEnum.EXT_BANK_FT_SERVICE_NAME.getParamName(), FT_BENF);
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		request.setRequestParamMap(requestParamMap);
		return request;
	}

}
