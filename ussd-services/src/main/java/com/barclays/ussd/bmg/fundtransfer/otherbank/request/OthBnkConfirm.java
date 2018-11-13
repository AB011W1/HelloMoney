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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.BeneData;

public class OthBnkConfirm implements BmgBaseRequestBuilder {

	@Override
	public USSDBaseRequest getRequestObject(
			RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {
		USSDBaseRequest request = new USSDBaseRequest();
		Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		String transNodeId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());

		if (userInputMap != null) {
		    String userBankSelection = userInputMap.get(USSDInputParamsEnum.EDIT_BENF_SELECT_BANK_NAME.getParamName());
		    String userBranchSelection = userInputMap.get(USSDInputParamsEnum.EDIT_BENF_SELECT_BRANCH_NAME.getParamName());

		    if (userBankSelection != null && StringUtils.isNotEmpty(userBankSelection)) {

		    List<UssdBranchLookUpDTO> bankList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
		    		USSDInputParamsEnum.EDIT_BENF_SELECT_BANK_NAME.getTranId());
		    		UssdBranchLookUpDTO bankCodeLookUpDTO = bankList.get(Integer.parseInt(userBankSelection) - 1);
		    		requestParamMap.put(USSDInputParamsEnum.EDIT_BENF_SELECT_BANK_NAME.getParamName(), bankCodeLookUpDTO.getBankCode());

		    	    if (userBranchSelection != null && StringUtils.isNotEmpty(userBranchSelection)) {
		    		List<UssdBranchLookUpDTO> branchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
		    			USSDInputParamsEnum.EDIT_BENF_SELECT_BRANCH_NAME.getTranId());
		    		UssdBranchLookUpDTO branchCodeLookUpDTO = branchList.get(Integer.parseInt(userBranchSelection) - 1);

		    		requestParamMap.put(USSDInputParamsEnum.EDIT_BENF_SELECT_BRANCH_NAME.getParamName(), branchCodeLookUpDTO.getBranchCode());
		    	    }

		    }
		    String userBenfSelection = userInputMap.get(USSDInputParamsEnum.EDIT_BENF_BENEFICIARY_NAME.getParamName());
		    List<BeneData> benfList = (List<BeneData>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.EDIT_BENF_BENEFICIARY_NAME.getTranId());
		    BeneData benfData = benfList.get(Integer.parseInt(userBenfSelection) - 1);
		    requestParamMap.put(USSDInputParamsEnum.EDIT_BENF_BENEFICIARY_NAME.getParamName(), benfData.getPayId());

		    requestParamMap.put(USSDInputParamsEnum.REG_BEN_EXT_PAYEE_TYPE.getParamName(), USSDConstants.EXTERNAL_PAYEE_TYPE);
		    requestParamMap.put(USSDInputParamsEnum.REG_BEN_EXT_BENF_NAME.getParamName(), userInputMap.get(USSDInputParamsEnum.REG_BEN_EXT_BENF_NAME
			    .getParamName()));

	    	requestParamMap.put(USSDInputParamsEnum.REG_BEN_EXT_BENF_NAME.getParamName(), userInputMap.get(USSDInputParamsEnum.REG_BEN_EXT_NICK_NAME
		    		    .getParamName()));

		    requestParamMap.put(USSDInputParamsEnum.REG_BEN_EXT_NICK_NAME.getParamName(), userInputMap.get(USSDInputParamsEnum.REG_BEN_EXT_NICK_NAME
			    .getParamName()));
		    requestParamMap.put(USSDInputParamsEnum.REG_BEN_EXT_ACC_NO.getParamName(), userInputMap.get(USSDInputParamsEnum.REG_BEN_EXT_ACC_NO
			    .getParamName()));
		    /*requestParamMap.put(USSDInputParamsEnum.REG_BEN_EXT_PAYEE_TYPE.getParamName(), userInputMap
			    .get(USSDInputParamsEnum.REG_BEN_EXT_PAYEE_TYPE.getParamName()));*/
		    requestParamMap.put(USSDInputParamsEnum.REG_BENF_BENF_ADDRESS.getParamName(),userInputMap
				    .get(USSDInputParamsEnum.REG_BENF_BENF_ADDRESS.getParamName()));
		    requestParamMap.put(USSDInputParamsEnum.REG_BENF_BENF_CITY.getParamName(),userInputMap
				    .get(USSDInputParamsEnum.REG_BENF_BENF_CITY.getParamName()));
		}
		// requestParamMap.put(BANK_CODE, "abcd");
		// requestParamMap.put(BENEFICIARY_NAME, "other barclays");
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

		request.setRequestParamMap(requestParamMap);
		return request;

	}

}
