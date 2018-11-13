package com.barclays.ussd.bmg.registerbenf.external;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class RegisterBenfExtSelectedBranchCode implements BmgBaseRequestBuilder {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RegisterBenfExtSelectedBranchCode.class);

	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	if (userInputMap == null) {
	    userInputMap = new HashMap<String, String>(5);
	    requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().setUserInputMap(userInputMap);
	}

	List<UssdBranchLookUpDTO> branchList = (List<UssdBranchLookUpDTO>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		.get(USSDInputParamsEnum.REG_BEN_EXT_BRANCH_CODE.getParamName());

	UssdBranchLookUpDTO branchLookUpDTO = branchList.get(Integer.parseInt(requestBuilderParamsDTO.getUserInput())-1);
	userInputMap.put(
			USSDInputParamsEnum.REG_BEN_EXT_BRANCH_CODE_LIST.getParamName(), branchLookUpDTO.getBranchCode()+USSDConstants.UNDERSCORE_SEPERATOR+branchLookUpDTO.getBranchName());

	/*requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().put(
			USSDInputParamsEnum.REG_BEN_EXT_BRANCH_CODE_LIST.getParamName(), branchLookUpDTO.getBranchCode()+USSDConstants.UNDERSCORE_SEPERATOR+branchLookUpDTO.getBranchName());
*/
	LOGGER.info("return the request object");
	return new USSDBaseRequest();
    }

}
