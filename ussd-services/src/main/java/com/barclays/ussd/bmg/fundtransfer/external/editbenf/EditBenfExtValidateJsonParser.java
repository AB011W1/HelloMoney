/**
 * EBFTPayeeListJsonParser.java
 */
package com.barclays.ussd.bmg.fundtransfer.external.editbenf;

import java.util.ArrayList;
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
import com.barclays.ussd.utils.jsonparsers.RegisterBenfExternalValidateJsonParser;
import com.barclays.ussd.utils.jsonparsers.bean.regbenf.internal.RegBenfIntBean;
import com.barclays.ussd.utils.jsonparsers.bean.regbenf.internal.ValidateRegBenfIntPayData;

/**
 * @author BTCI
 * 
 */
public class EditBenfExtValidateJsonParser implements BmgBaseJsonParser {
    private static final String BRANCH_CODE_LBL = "label.branchcode";
    private static final String NICK_NAME_LABEL = "label.nickname";
    private static final String ACCNT_NO_LABEL = "label.accoun.number";
    private static final String BANK_CODE_LBL = "lable.bankcode";
    private static final String BANK_NAME_LBL = "lable.bankname";
    private static final String BRANCH_NAME_LBL = "label.branchname";
    private static final String BENE_NAME_LABEL = "lable.benename";
    private static final String CONFIRM_LABEL = "label.confirm";
    private static final Logger LOGGER = Logger.getLogger(RegisterBenfExternalValidateJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {

	    RegBenfIntBean regBenfInt = mapper.readValue(jsonString, RegBenfIntBean.class);
	    if (regBenfInt != null) {
		if (regBenfInt.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(regBenfInt.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(regBenfInt.getPayData(), responseBuilderParamsDTO);
		    List<String> txnRefNo = new ArrayList<String>(1);
		    txnRefNo.add(regBenfInt.getPayData().getTxnRefNo());

		    Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		    if (txSessions == null) {
			txSessions = new HashMap<String, Object>(5);
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		    }
		    // set the payee accnt list to the session
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.REG_BEN_INT_VALIDATE.getTranId(), txnRefNo);
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
     * @param validateRegBenfIntPayData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ValidateRegBenfIntPayData validateRegBenfIntPayData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	/*
	 * List<BranchLookUpDTO> bankList = (List<BranchLookUpDTO>)
	 * ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE_LIST.getTranId()); BranchLookUpDTO bankCodeLookUpDTO =
	 * bankList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.REG_BEN_EXT_BANK_CODE_LIST.getParamName()))-1);
	 */
	String userBranchSelection = userInputMap.get(USSDInputParamsEnum.EDIT_BENF_EXT_BRANCH_CODE_LIST.getParamName());
	String userBankSelection = userInputMap.get(USSDInputParamsEnum.EDIT_BENF_EXT_BANK_CODE_LIST.getParamName());
	UssdBranchLookUpDTO branchCodeLookUpDTO = null;
	UssdBranchLookUpDTO bankCodeLookUpDTO = null;
	if (userBranchSelection != null && StringUtils.isNotEmpty(userBranchSelection)) {
	    List<UssdBranchLookUpDTO> branchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.EDIT_BENF_EXT_BRANCH_CODE_LIST.getTranId());
	    branchCodeLookUpDTO = branchList.get(Integer.parseInt(userBranchSelection) - 1);
	}
	if (userBankSelection != null && StringUtils.isNotEmpty(userBankSelection)) {
	    List<UssdBranchLookUpDTO> branchList = (List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.EDIT_BENF_EXT_BANK_CODE_LIST.getTranId());
	    bankCodeLookUpDTO = branchList.get(Integer.parseInt(userBankSelection) - 1);
	}

	if (validateRegBenfIntPayData != null && validateRegBenfIntPayData.getBeneficiaryInfo() != null) {
	    String confirmLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(CONFIRM_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String accntNoLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(ACCNT_NO_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String bankNameLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(BANK_NAME_LBL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String beneNameLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(BENE_NAME_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String nickNameLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(NICK_NAME_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String branchNameLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(BRANCH_NAME_LBL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

	    pageBody.append(accntNoLabel);
	    // pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(validateRegBenfIntPayData.getBeneficiaryInfo().getActNo());
	    pageBody.append(USSDConstants.NEW_LINE);
	    if (userBankSelection != null && StringUtils.isNotEmpty(userBankSelection)) {
		pageBody.append(bankNameLabel);
		// pageBody.append(validateRegBenfIntPayData.getBeneficiaryInfo().getBranchInfo().getBnkCde());
		pageBody.append(bankCodeLookUpDTO.getBankName());
		pageBody.append(USSDConstants.NEW_LINE);
	    }
	    if (userBranchSelection != null && StringUtils.isNotEmpty(userBranchSelection)) {
		pageBody.append(branchNameLabel);
		// pageBody.append(USSDConstants.NEW_LINE);
		// pageBody.append(validateRegBenfIntPayData.getBeneficiaryInfo().getBranchInfo().getBrnCde());
		pageBody.append(branchCodeLookUpDTO.getBranchName());
		pageBody.append(USSDConstants.NEW_LINE);
	    }

	    // if (!USSDInputParamsEnum.EDIT_BENF_EXT_VALIDATE.getTranId().equalsIgnoreCase(responseBuilderParamsDTO.getTranDataId())) {
	    pageBody.append(beneNameLabel);
	    pageBody.append(validateRegBenfIntPayData.getBeneficiaryInfo().getBeneNam());
	    pageBody.append(USSDConstants.NEW_LINE);
	    // }
	    pageBody.append(nickNameLabel);
	    // pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(validateRegBenfIntPayData.getBeneficiaryInfo().getPayNckNam());

	    if (!responseBuilderParamsDTO.isErrorneousPage()) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(confirmLabel);
	    }

	}

	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageBody(pageBody.toString());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.SPACED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TEN.getSequenceNo());

    }
}
