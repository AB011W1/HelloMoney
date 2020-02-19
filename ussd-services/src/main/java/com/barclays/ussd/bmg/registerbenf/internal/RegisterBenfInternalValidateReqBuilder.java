package com.barclays.ussd.bmg.registerbenf.internal;

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
import com.barclays.ussd.sysprefs.services.ListValueResServiceImpl;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;

public class RegisterBenfInternalValidateReqBuilder implements BmgBaseRequestBuilder {
    @Autowired
    private ListValueResServiceImpl listValueResService;
    private USSDSessionManagement ussdSessionMgmt;

    public ListValueResServiceImpl getListValueResService() {
	return listValueResService;
    }

    public void setListValueResService(ListValueResServiceImpl listValueResService) {
	this.listValueResService = listValueResService;
    }

    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();

	ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());
	if (userInputMap != null) {

		/**
		 * CR73 changes incorporated
		 */
		String isFromSaveBeneficiary = StringUtils.EMPTY;
		if(ussdSessionMgmt.getTxSessions()!=null){
			isFromSaveBeneficiary = ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.INT_NR_FT_CONFIRM.getTranId())== null?"":(String) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.INT_NR_FT_CONFIRM.getTranId());
		}

		//Changes due to CR 73
	    String userBranchSelection = StringUtils.EMPTY.equals(isFromSaveBeneficiary)?userInputMap.get(USSDInputParamsEnum.REG_BEN_INT_BRANCH_CODE.getParamName()):userInputMap.get(USSDInputParamsEnum.INT_NR_FT_BRANCH_CODE.getParamName());
	    if (userBranchSelection != null && StringUtils.isNotEmpty(userBranchSelection)) {
		List<UssdBranchLookUpDTO> branchList = StringUtils.EMPTY.equals(isFromSaveBeneficiary)?(List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
			USSDInputParamsEnum.REG_BEN_INT_BRANCH_CODE.getTranId()):(List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
		    		USSDInputParamsEnum.INT_NR_FT_BRANCH_CODE.getTranId());
		UssdBranchLookUpDTO branchCodeLookUpDTO = branchList.get(Integer.parseInt(userBranchSelection) - 1);
		requestParamMap.put(USSDInputParamsEnum.REG_BEN_INT_BRANCH_CODE.getParamName(), branchCodeLookUpDTO.getBranchCode());
	    }

	    //Changes due to CR 73
	    String benefAccNo = StringUtils.EMPTY.equals(isFromSaveBeneficiary)?userInputMap.get(USSDInputParamsEnum.REG_BEN_INT_ACC_NO.getParamName()):userInputMap.get(USSDInputParamsEnum.INT_NR_FT_TO_AC.getParamName());
	    
	    //Added for MZBRB other Bank One-off
	    if(null == benefAccNo)
	    	benefAccNo = userInputMap.get(USSDInputParamsEnum.REG_BENF_GET_NIB_NO.getParamName());
	        
	    String nickName = StringUtils.EMPTY.equals(isFromSaveBeneficiary)?userInputMap.get(USSDInputParamsEnum.REG_BEN_INT_NICK_NAME.getParamName()):userInputMap.get(USSDInputParamsEnum.FUND_TRANSFER_OTHER_BARCLAYS_SAVE_BEN_INT_NICK_NAME.getParamName());
	    String payeeType = StringUtils.EMPTY.equals(isFromSaveBeneficiary)?userInputMap.get(USSDInputParamsEnum.REG_BEN_INT_PAYEE_TYPE.getParamName()):USSDConstants.INTERNAL_PAYEE_TYPE;
	    
	    if(null != userInputMap.get(USSDInputParamsEnum.REG_BENF_GET_NIB_NO.getParamName()))
	    	payeeType = "DAC";

	    requestParamMap.put(USSDInputParamsEnum.REG_BEN_INT_ACC_NO.getParamName(), benefAccNo);
	    requestParamMap.put(USSDInputParamsEnum.REG_BEN_INT_NICK_NAME.getParamName(), nickName);
	    requestParamMap.put(USSDInputParamsEnum.REG_BEN_INT_PAYEE_TYPE.getParamName(), payeeType);
	    // requestParamMap.putAll(userInputMap);
	}
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

	request.setRequestParamMap(requestParamMap);
	return request;

    }

}
