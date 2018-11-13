package com.barclays.ussd.utils.jsonparsers;

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

import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.regbenf.internal.BranchInfo;
import com.barclays.ussd.utils.jsonparsers.bean.regbenf.internal.RegBenfIntBean;
import com.barclays.ussd.utils.jsonparsers.bean.regbenf.internal.ValidateRegBenfIntPayData;

public class RegisterBenfInternalValidateJsonParser implements BmgBaseJsonParser, ScreenSequenceCustomizer {
    private static final String NICK_NAME_LABEL = "label.nickname";
    private static final String ACCNT_NO_LABEL = "label.accoun.number";
    private static final String BANK_NAME_LBL = "lable.bankname";
    // private static final String BRANCH_NAME_LBL = "label.branchcode";
    private static final String BRANCH_NAME_LBL = "label.branchname";
    private static final String BENE_NAME_LABEL = "lable.benename";
    private static final String CONFIRM_LABEL = "label.confirm";
    private static final Logger LOGGER = Logger.getLogger(RegisterBenfInternalValidateJsonParser.class);

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
    @SuppressWarnings("unchecked")
    private MenuItemDTO renderMenuOnScreen(ValidateRegBenfIntPayData validateRegBenfIntPayData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	UssdBranchLookUpDTO branchCodeLookUpDTO = null;
	String userBranchSelection = userInputMap.get(USSDInputParamsEnum.REG_BEN_INT_BRANCH_CODE.getParamName());
	/**
	 * CR73 changes incorporated
	 */
	String isFromSaveBeneficiary = StringUtils.EMPTY;
	if(ussdSessionMgmt.getTxSessions()!=null){
		isFromSaveBeneficiary = ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.INT_NR_FT_CONFIRM.getTranId())== null?"":(String) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.INT_NR_FT_CONFIRM.getTranId());
	}

	if (userBranchSelection != null && StringUtils.isNotEmpty(userBranchSelection)) {

	    List<UssdBranchLookUpDTO> branchList = StringUtils.EMPTY.equals(isFromSaveBeneficiary)?(List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
			    USSDInputParamsEnum.REG_BEN_INT_BRANCH_CODE.getTranId()):(List<UssdBranchLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
					    USSDInputParamsEnum.INT_NR_FT_BRANCH_CODE.getTranId());
	    branchCodeLookUpDTO = branchList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.REG_BEN_INT_BRANCH_CODE.getParamName())) - 1);
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
	    if (userBranchSelection != null && StringUtils.isNotEmpty(userBranchSelection)) {
		if (!USSDInputParamsEnum.REG_BEN_INT_VALIDATE.getTranId().equalsIgnoreCase(responseBuilderParamsDTO.getTranDataId())) {
		    pageBody.append(bankNameLabel);
		    // pageBody.append(validateRegBenfIntPayData.getBeneficiaryInfo().getBranchInfo().getBnkCde());
		    pageBody.append(branchCodeLookUpDTO.getBankName());
		    pageBody.append(USSDConstants.NEW_LINE);

		}
		BranchInfo branchInfo = validateRegBenfIntPayData.getBeneficiaryInfo().getBranchInfo();
		if (branchInfo != null && branchInfo.getBrnCde() != null && StringUtils.isNotEmpty(branchInfo.getBrnCde())) {
		    pageBody.append(branchNameLabel);
		    // pageBody.append(USSDConstants.NEW_LINE);
		    // pageBody.append(validateRegBenfIntPayData.getBeneficiaryInfo().getBranchInfo().getBrnCde());
		    pageBody.append(branchCodeLookUpDTO.getBranchName());
		    pageBody.append(USSDConstants.NEW_LINE);
		}
	    }

	    if (!USSDInputParamsEnum.REG_BEN_INT_VALIDATE.getTranId().equalsIgnoreCase(responseBuilderParamsDTO.getTranDataId())) {
		pageBody.append(beneNameLabel);
		pageBody.append(validateRegBenfIntPayData.getBeneficiaryInfo().getBeneNam());
		pageBody.append(USSDConstants.NEW_LINE);
	    }
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
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHT.getSequenceNo());

    }

    /**
	 * CR 73
	 */
	@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		String isFromSaveBeneficiary = StringUtils.EMPTY;
		if(ussdSessionMgmt.getTxSessions()!=null){
			isFromSaveBeneficiary = ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.INT_NR_FT_CONFIRM.getTranId())== null?"":(String) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.INT_NR_FT_CONFIRM.getTranId());
		}
		int nextSequence = USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHT.getSequenceNo();

		if(!isFromSaveBeneficiary.equals(StringUtils.EMPTY) && isFromSaveBeneficiary.equals(FundTransferConstants.FUND_TRANSFER_OTHER_BARCLAYS_SAVE_BILLER)){
			nextSequence = USSDSequenceNumberEnum.SEQUENCE_NUMBER_ELEVEN.getSequenceNo();
		}
		return nextSequence;
	}
}
