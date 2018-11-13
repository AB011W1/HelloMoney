package com.barclays.ussd.bmg.fundtransfer.internal.editbenf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTBeneficiary;

public class EditBenfInternalValidateRequestBuilder implements BmgBaseRequestBuilder {
    @Autowired
    private USSDSessionManagement ussdSessionMgmt;

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

	ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	if (userInputMap != null) {

	    String branchListInput = userInputMap.get(USSDInputParamsEnum.EDIT_BEN_INT_BRANCH_LIST.getParamName());
	    if (branchListInput != null && StringUtils.isNotEmpty(branchListInput)) {
		List<UssdBranchLookUpDTO> branchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
			USSDInputParamsEnum.EDIT_BEN_INT_BRANCH_LIST.getTranId());
		UssdBranchLookUpDTO branchCodeLookUpDTO = branchList.get(Integer.parseInt(branchListInput) - 1);
		requestParamMap.put(USSDInputParamsEnum.EDIT_BEN_INT_BRANCH_LIST.getParamName(), branchCodeLookUpDTO.getBranchCode());
	    }

	    requestParamMap.put(USSDInputParamsEnum.EDIT_BEN_INT_ACC_NO.getParamName(), userInputMap.get(USSDInputParamsEnum.EDIT_BEN_INT_ACC_NO
		    .getParamName()));

	    requestParamMap.put(USSDInputParamsEnum.EDIT_BEN_INT_NICK_NAME.getParamName(), userInputMap.get(USSDInputParamsEnum.REG_BEN_INT_NICK_NAME
		    .getParamName()));
	    requestParamMap.put(USSDInputParamsEnum.EDIT_BEN_INT_PAYEE_TYPE.getParamName(), userInputMap
		    .get(USSDInputParamsEnum.EDIT_BEN_INT_PAYEE_TYPE.getParamName()));

	    List<OBAFTBeneficiary> lstPayee = (List<OBAFTBeneficiary>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		    USSDInputParamsEnum.EDIT_BEN_INT_BENF_ID.getTranId());


	    // To get Old Benf Name
	    OBAFTBeneficiary benfPayee=lstPayee.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.EDIT_BEN_INT_BENF_ID.getParamName()))
	    		- 1);

	    requestParamMap.put(USSDInputParamsEnum.EDIT_BEN_INT_BENF_ID.getParamName(), benfPayee.getPayId());
	    requestParamMap.put("beneficiaryOldNickName",benfPayee.getDisLbl());
	}
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	request.setRequestParamMap(requestParamMap);
	return request;

    }

}
