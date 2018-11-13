package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.regbenf.internal.RegBenfIntBean;

public class GhipsRegisterBenefAddJsonParser implements BmgBaseJsonParser {

	private static final String RESULT_MESSAGE = "label.ghips.register.benef.successful";
	private static final String NICK_NAME_LABEL = "label.nickname";
	private static final String ACCNT_NO_LABEL = "label.accoun.number";
	private static final String BANK_NAME_LBL = "label.ghips.benef.bank.name";
    private static final Logger LOGGER = Logger.getLogger(GhipsRegisterBenefAddJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
    	MenuItemDTO menuDTO = null;
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    	    RegBenfIntBean regBenfInt = mapper.readValue(responseBuilderParamsDTO.getJsonString(), RegBenfIntBean.class);
    	    if (regBenfInt != null) {
    		if (regBenfInt.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(regBenfInt.getPayHdr().getResCde())) {
    		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, regBenfInt);
    		} else if (regBenfInt.getPayHdr() != null) {
    		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
    		    throw new USSDNonBlockingException(regBenfInt.getPayHdr().getResCde());
    		} else {
    		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
    		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
    		}
    	    }
    	} catch (JsonParseException e) {
    	    LOGGER.error("JsonParseException : ", e);
    	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
    	} catch (JsonMappingException e) {
    	    LOGGER.error("JsonParseException : ", e);
    	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
    	} catch (Exception e) {
    	    LOGGER.error("Exception : ", e);
    	    if (e instanceof USSDNonBlockingException) {
    		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
    	    } else {
    		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
    	    }
    	}

    	return menuDTO;
    }

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @param regBenfInt
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, RegBenfIntBean regBenfInt) {

    MenuItemDTO menuItemDTO = new MenuItemDTO();
    StringBuilder pageBody = new StringBuilder();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	String resultLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(RESULT_MESSAGE,
		new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	pageBody.append(resultLabel);
	Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	if (txSessions == null) {
		txSessions = new HashMap<String, Object>(8);
		responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
	}

	String accountNumber = regBenfInt.getPayData().getBeneficiaryInfo().getActNo();
	String benefNickName = regBenfInt.getPayData().getBeneficiaryInfo().getPayNckNam();
	String selectedBankCode = regBenfInt.getPayData().getBeneficiaryInfo().getBranchInfo().getBnkCde();
	UssdBranchLookUpDTO branchCodeLookUpDTO = null;


	if (selectedBankCode != null
			&& StringUtils.isNotEmpty(selectedBankCode)) {
		List<UssdBranchLookUpDTO> branchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt
				.getTxSessions().get(
						USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_LIST
								.getTranId());
		Map<String, String> userInputMap=ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		branchCodeLookUpDTO = branchList.get(Integer
				.parseInt(userInputMap.get(USSDInputParamsEnum.GHIPS_REGISTER_BENEF_BANK_LIST.getParamName()))-1);
	}



	String accntNoLabel = responseBuilderParamsDTO
	.getUssdResourceBundle().getLabel(
			ACCNT_NO_LABEL,
			new Locale(ussdSessionMgmt.getUserProfile()
					.getLanguage(), ussdSessionMgmt
					.getUserProfile().getCountryCode()));

	String nickNameLabel = responseBuilderParamsDTO
	.getUssdResourceBundle().getLabel(
			NICK_NAME_LABEL,
			new Locale(ussdSessionMgmt.getUserProfile()
					.getLanguage(), ussdSessionMgmt
					.getUserProfile().getCountryCode()));

	String bankNameLabel = responseBuilderParamsDTO
	.getUssdResourceBundle().getLabel(
			BANK_NAME_LBL,
			new Locale(ussdSessionMgmt.getUserProfile()
					.getLanguage(), ussdSessionMgmt
					.getUserProfile().getCountryCode()));

	pageBody.append(USSDConstants.NEW_LINE);
	pageBody.append(accntNoLabel);
	pageBody.append(accountNumber);
	pageBody.append(USSDConstants.NEW_LINE);
	pageBody.append(bankNameLabel);
	pageBody.append(branchCodeLookUpDTO.getBankName());
	pageBody.append(USSDConstants.NEW_LINE);
	pageBody.append(nickNameLabel);
	pageBody.append(benefNickName);

	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setPageBody(pageBody.toString());
	menuItemDTO.setStatus(USSDConstants.STATUS_END);
	menuItemDTO.setPaginationType(PaginationEnum.SPACED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());
    }

}
