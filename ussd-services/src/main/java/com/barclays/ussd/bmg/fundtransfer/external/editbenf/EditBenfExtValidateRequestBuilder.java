package com.barclays.ussd.bmg.fundtransfer.external.editbenf;

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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.BeneData;

public class EditBenfExtValidateRequestBuilder implements BmgBaseRequestBuilder {

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();

	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());

	if (userInputMap != null) {
	    String userBankSelection = userInputMap.get(USSDInputParamsEnum.EDIT_BENF_EXT_BANK_CODE_LIST.getParamName());
	    String userBranchSelection = userInputMap.get(USSDInputParamsEnum.EDIT_BENF_EXT_BRANCH_CODE_LIST.getParamName());
	    if (userBankSelection != null && StringUtils.isNotEmpty(userBankSelection)) {
		List<UssdBranchLookUpDTO> bankList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
			USSDInputParamsEnum.EDIT_BENF_EXT_BANK_CODE_LIST.getTranId());
		UssdBranchLookUpDTO bankCodeLookUpDTO = bankList.get(Integer.parseInt(userBankSelection) - 1);
		requestParamMap.put(USSDInputParamsEnum.EDIT_BENF_EXT_BANK_CODE_LIST.getParamName(), bankCodeLookUpDTO.getBankCode());
	    }
	    if (userBranchSelection != null && StringUtils.isNotEmpty(userBranchSelection)) {
		List<UssdBranchLookUpDTO> branchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
			USSDInputParamsEnum.EDIT_BENF_EXT_BRANCH_CODE_LIST.getTranId());
		UssdBranchLookUpDTO branchCodeLookUpDTO = branchList.get(Integer.parseInt(userBranchSelection) - 1);

		requestParamMap.put(USSDInputParamsEnum.EDIT_BENF_EXT_BRANCH_CODE_LIST.getParamName(), branchCodeLookUpDTO.getBranchCode());
	    }

	    String userBenfSelection = userInputMap.get(USSDInputParamsEnum.EDIT_BENF_EXT_BENEF_LIST.getParamName());
	    List<BeneData> benfList = (List<BeneData>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.EDIT_BENF_EXT_BENEF_LIST.getTranId());
	    BeneData benfData = benfList.get(Integer.parseInt(userBenfSelection) - 1);
	    requestParamMap.put(USSDInputParamsEnum.EDIT_BENF_EXT_BENEF_LIST.getParamName(), benfData.getPayId());

	    requestParamMap.put(USSDInputParamsEnum.EDIT_BENF_EXT_PAYEE_TYPE.getParamName(), USSDConstants.EXTERNAL_PAYEE_TYPE);
	    requestParamMap.put(USSDInputParamsEnum.EDIT_BENF_EXT_BENF_NAME.getParamName(), userInputMap
		    .get(USSDInputParamsEnum.EDIT_BENF_EXT_BENF_NAME.getParamName()));
	    requestParamMap.put(USSDInputParamsEnum.EDIT_BENF_EXT_NICK_NAME.getParamName(), userInputMap
		    .get(USSDInputParamsEnum.EDIT_BENF_EXT_NICK_NAME.getParamName()));
	    requestParamMap.put(USSDInputParamsEnum.EDIT_BENF_EXT_ACC_NO.getParamName(), userInputMap.get(USSDInputParamsEnum.EDIT_BENF_EXT_ACC_NO
		    .getParamName()));
	    requestParamMap.put(USSDInputParamsEnum.EDIT_BENF_EXT_PAYEE_TYPE.getParamName(), userInputMap
		    .get(USSDInputParamsEnum.EDIT_BENF_EXT_PAYEE_TYPE.getParamName()));

	}
	// requestParamMap.put(BANK_CODE, "abcd");
	// requestParamMap.put(BENEFICIARY_NAME, "other barclays");
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	request.setRequestParamMap(requestParamMap);
	return request;

    }

}
