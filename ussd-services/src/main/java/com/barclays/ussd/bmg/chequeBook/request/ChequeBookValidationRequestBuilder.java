package com.barclays.ussd.bmg.chequeBook.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.UssdBranchLocatorLookUpDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq.AccountChequBookDetails;
import com.barclays.ussd.utils.jsonparsers.bean.request.chqueBookReq.ChequeBookSizeList;

public class ChequeBookValidationRequestBuilder implements BmgBaseRequestBuilder {

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	// Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	if (userInputMap == null) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
	}

	List<ChequeBookSizeList> lstCheckSize = (List<ChequeBookSizeList>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.CHECK_BK_SIZE.getTranId());
	String bookletNo = lstCheckSize.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.CHECK_BK_SIZE.getParamName())) - 1).getVal();

	userInputMap.put(USSDInputParamsEnum.CHECK_BK_SIZE.getParamName(), bookletNo);

	List<AccountChequBookDetails> lst = (List<AccountChequBookDetails>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.CHECK_BK_SRC_ACT.getTranId());
	String srcAcntIndex = userInputMap.get(USSDInputParamsEnum.CHECK_BK_SRC_ACT.getParamName());
	AccountChequBookDetails srcAcnt = lst.get(Integer.parseInt(srcAcntIndex) - 1);

	String selectedSrcActNo = srcAcnt.getActNo();
	String collectionBranchCode = srcAcnt.getBrnCde();
	String collectionBranchName = srcAcnt.getBrnNam();

	List<UssdBranchLocatorLookUpDTO> branchList = (List<UssdBranchLocatorLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.CHECK_BK_BRANCH_LIST.getTranId());
	if (branchList != null) {
	    UssdBranchLocatorLookUpDTO selectedBranch = branchList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.CHECK_BK_BRANCH_LIST
		    .getParamName())) - 1);
	    collectionBranchCode = selectedBranch.getBranchCode();
	    collectionBranchName = selectedBranch.getBranchName();

	}
	Map<String, String> requestParamMap = new HashMap<String, String>(6);
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_ACCOUNT_VALUE, selectedSrcActNo);
	requestParamMap.put(USSDConstants.BMG_BRN_CDE, collectionBranchCode);
	requestParamMap.put(USSDConstants.BMG_BRN_NAM, collectionBranchName);
	requestParamMap.put(USSDInputParamsEnum.CHECK_BK_SIZE.getParamName(), bookletNo);
	requestParamMap.put(USSDInputParamsEnum.CHECK_BK_VALIDATE.getParamName(), "1");
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode()); // Cheque boook Validation
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	request.setRequestParamMap(requestParamMap);

	return request;
    }
}
