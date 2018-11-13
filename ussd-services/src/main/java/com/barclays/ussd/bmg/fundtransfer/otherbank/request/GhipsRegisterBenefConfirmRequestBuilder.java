package com.barclays.ussd.bmg.fundtransfer.otherbank.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class GhipsRegisterBenefConfirmRequestBuilder implements
BmgBaseRequestBuilder {

	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
	throws USSDNonBlockingException, USSDBlockingException {
		USSDBaseRequest request = new USSDBaseRequest();
		Map<String, String> userInputMap = requestBuilderParamsDTO
		.getUssdSessionMgmt().getUserTransactionDetails()
		.getUserInputMap();

		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO
		.getUssdSessionMgmt();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());

		String userEnteredAccountNum = userInputMap
		.get(USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_ACC_NO
				.getParamName());
		String userEnteredBenefName = userInputMap
		.get(USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_NICK_NAME
				.getParamName());
		List<UssdBranchLookUpDTO> branchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt
		.getTxSessions().get(
				USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_LIST
				.getTranId());

		String userEnteredBankCode = branchList.get(Integer.parseInt(userInputMap
				.get(USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_LIST
						.getParamName()))-1).getBankCode();

		String bankName = branchList.get(Integer.parseInt(userInputMap
				.get(USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_LIST
						.getParamName()))-1).getBankName();

		if (userEnteredAccountNum != null
				&& StringUtils.isNotEmpty(userEnteredAccountNum)) {
			requestParamMap.put(USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_ACC_NO
					.getParamName(), userEnteredAccountNum);
			requestParamMap.put("beneficiaryName", String.valueOf(ussdSessionMgmt
					.getTxSessions().get(USSDConstants.GHIPPS_CUSTOMER_NAME)));
			requestParamMap.put(USSDInputParamsEnum.REG_BEN_INT_PAYEE_TYPE.getParamName(), USSDConstants.GHIPPSPAY_PAYEE_TYPE);
			requestParamMap.put(USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_LIST.getParamName(), userEnteredBankCode);
			requestParamMap.put("bankName", bankName);
			requestParamMap.put(USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_NICK_NAME
					.getParamName(), userEnteredBenefName);
			requestParamMap.put(USSDInputParamsEnum.REG_BEN_EXT_BRANCH_CODE_LIST
					.getParamName(), "GP");
		}
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME,
				requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME,
				USSDConstants.BMG_SERVICE_VERSION_VALUE);
		request.setRequestParamMap(requestParamMap);
		return request;
	}
}
