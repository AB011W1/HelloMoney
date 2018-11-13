package com.barclays.ussd.utils.jsonparsers;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.SelfRegistrationChallengeQnResponse;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

public class SelfRegisterTnC1Parser implements BmgBaseJsonParser,ScreenSequenceCustomizer {
	 private static final Logger LOGGER = Logger.getLogger(SelfRegisterInitParser.class);
	    private static final String BEM_ERRCODE_USERREGEXISTS = "BEM08781";// CRYPTO 9301 AliasAlreadyUsed
	    private static final String BEM_ERRCODE_08788 = "BEM08788";// CRYPTO 9003 QuestionAlreadyExists
	    private static final String BEM_ERRCODE_08776 = "BEM08776";// CRYPTO 9111 SubjectInUse
	    private static final String BEM_ERRCODE_08778 = "BEM08778";// CRYPTO 9113 TokenExists

	    private static final String BEM_ERRCODE_08793 = "BEM08793";// Input mobile number does not match with CBS mobile number BMG validation
	    private static final String BMG_ERRCODE_REG01171 = "REG01171";// CR11 validation, invalid account
	    private static final String BMG_ERRCODE_REG01173 = "REG01173"; // CR6 validation and check if the user has input account/branch

	    private static final String USER_ACCOUNT_NOT_FOUND = "BEM06754";
	    private static final String USER_ACCOUNT_DOES_NOT_EXIST = "BEM7501";

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	MenuItemDTO menuDTO = null;
	menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
	return menuDTO;
    }

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();
	try {
	    SelfRegistrationChallengeQnResponse selfRegInitResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
		    SelfRegistrationChallengeQnResponse.class);
	    String tranDataId=responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
	    if (selfRegInitResponse != null && selfRegInitResponse.getPayHdr() != null) {
		PayHdr payHdr = selfRegInitResponse.getPayHdr();

		if (USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(payHdr.getResCde())) {
		    // menuDTO = null;
		}  else if (BEM_ERRCODE_08788.equalsIgnoreCase(payHdr.getResCde())) {
		    LOGGER.error("Mobile number not found: " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDBlockingException(USSDExceptions.BEM08793.getBmgCode());
		} else if (BEM_ERRCODE_08793.equalsIgnoreCase(payHdr.getResCde())) {
		    LOGGER.error("Input mobile number does not match with CBS mobile number: " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDBlockingException(USSDExceptions.BEM08793.getBmgCode());
		} else if (BMG_ERRCODE_REG01171.equalsIgnoreCase(payHdr.getResCde())&&tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")) {
		    LOGGER.error("Invalid account as per CR11 check: " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(USSDExceptions.SELF_REG_INVALID_ACCOUNT_FP.getBmgCode());
		} else if (BMG_ERRCODE_REG01171.equalsIgnoreCase(payHdr.getResCde())) {
		    LOGGER.error("Invalid account as per CR11 check: " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDBlockingException(USSDExceptions.SELF_REG_INVALID_ACCOUNT.getBmgCode());
		} else if (BMG_ERRCODE_REG01173.equalsIgnoreCase(payHdr.getResCde())) {
		    LOGGER.error("Invalid account as per CR6 check or input account/branch is not matching with user's account: "
			    + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDBlockingException(USSDExceptions.SELF_REG_INVALID_ACCOUNT.getBmgCode());
		} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BMB90004.getBmgCode(), payHdr.getResCde())
			|| StringUtils.equalsIgnoreCase(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(), payHdr.getResCde())) {
		    throw new USSDBlockingException(USSDExceptions.BMB90004.getBmgCode());
		} else if (StringUtils.equalsIgnoreCase(USER_ACCOUNT_NOT_FOUND, payHdr.getResCde())) {
		    throw new USSDBlockingException(USSDExceptions.SELF_REG_INVALID_CREDENTIALS.getBmgCode());
		} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM06001.getBmgCode(), payHdr.getResCde())) {
		    throw new USSDBlockingException(USSDExceptions.BEM06001.getBmgCode());
		} else if (StringUtils.equalsIgnoreCase(USER_ACCOUNT_DOES_NOT_EXIST, payHdr.getResCde())) {
		    throw new USSDBlockingException(USSDExceptions.SELF_REG_INVALID_ACCOUNT.getBmgCode());
		} else {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(payHdr.getResCde());
		}
	    } else {
		LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }

	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else if (e instanceof USSDBlockingException) {
		throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());
    }

	@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
    	String tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
    	if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")){
    		seqNo=USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo();
    	}
    	return seqNo;
	}
}
