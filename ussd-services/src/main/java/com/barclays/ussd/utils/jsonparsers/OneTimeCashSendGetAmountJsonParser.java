package com.barclays.ussd.utils.jsonparsers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.LongValidator;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.sysprefs.services.ListValueCacheDTO;
import com.barclays.ussd.sysprefs.services.ListValueResByGroupServiceResponse;
import com.barclays.ussd.sysprefs.services.ListValueResServiceImpl;
import com.barclays.ussd.sysprefs.services.ListValueResServiceRequest;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.SystemPreferenceConstants;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.RetrieveAccList;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMinMaxRangeValidator;
import com.barclays.ussd.validation.USSDMultiRangeValidator;

public class OneTimeCashSendGetAmountJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator
// , ScreenSequenceCustomizer
{
    private static final Logger LOGGER = Logger.getLogger(OneTimeCashSendGetSrcAcctJsonParser.class);
    private static final String CASH_SEND_LABEL = "label.cashsend.amount";
    private static final String SYSTEM_ID_UB = "UB";
    private static final String SERVICE_ENABLED_TRUE = "Y";
    private String multiAmtSndr = "";

    @Autowired
    private ListValueResServiceImpl listValueResService;
    @Autowired
    private SystemParameterService systemParameterService;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	try {

	    RetrieveAccList accList = mapper.readValue(jsonString, RetrieveAccList.class);
	    if (accList != null) {
		if (accList.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(accList.getPayHdr().getResCde())) {
		    List<AccountDetails> srcLst = accList.getPayData().getSrcLst();
		    if (srcLst == null || srcLst.isEmpty() || srcLst.size() == 0) {
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getUssdErrorCode());
		    }
		    Collections.sort(accList.getPayData().getSrcLst(), new OneTimeCashSendGetAmountComparator());
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
		    if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
			Map<String, Object> txSessionMap = new HashMap<String, Object>(5);
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessionMap);
		    }
		    // set the from accnt list to the session
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(
			    USSDInputParamsEnum.ONE_TIME_CASH_SEND_SRC_ACCT_LIST.getTranId(), accList.getPayData().getSrcLst());

		} else if (accList.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(accList.getPayHdr().getResCde());
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
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();

	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	multiAmtSndr = getSystemPreferenceData(ussdSessionMgmt.getUserProfile(), SystemPreferenceConstants.SYS_PARAM_CS,
			SystemPreferenceConstants.CS_MULTIPLE_AMT);

	String maximumAmt = getSystemPreferenceData(ussdSessionMgmt.getUserProfile(), SystemPreferenceConstants.SYS_PARAM_CS,
		SystemPreferenceConstants.CS_MAX_AMT);
	String dailyLimitAmt = getSystemPreferenceData(ussdSessionMgmt.getUserProfile(), SystemPreferenceConstants.SYS_PARAM_CS,
		SystemPreferenceConstants.CS_MAX_DAYAMT);

	List<String> params = new ArrayList<String>(1);
	params.add(multiAmtSndr);
	params.add(maximumAmt);
	params.add(dailyLimitAmt);
	String[] paramArray = params.toArray(new String[params.size()]);

	String cashsendAmountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(CASH_SEND_LABEL, paramArray,
		new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

	pageBody.append(cashsendAmountLabel);
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	menuItemDTO.setPageBody(pageBody.toString());
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());

    }

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<AccountDetails> srcAccList = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.ONE_TIME_CASH_SEND_SRC_ACCT_LIST.getTranId());
	if (srcAccList != null && srcAccList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.ONE_TIME_CASH_SEND_SRC_ACCT_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();
	}
	return seqNo;
    }

    protected String getSysParamValue(String businessId, String countryCode, String sysParamKey) {
	String paramValue = "";
	SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
	systemParameterDTO.setBusinessId(businessId);
	systemParameterDTO.setSystemId(SYSTEM_ID_UB);
	systemParameterDTO.setActivityId(SystemParameterConstant.COMMON_ACTIVITY_ID);
	systemParameterDTO.setParameterId(sysParamKey);

	SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
	systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);

	SystemParameterServiceResponse response = systemParameterService.getSystemParameter(systemParameterServiceRequest);

	if (response != null && response.getSystemParameterDTO() != null) {
	    paramValue = response.getSystemParameterDTO().getParameterValue();
	} else {
	    paramValue = SERVICE_ENABLED_TRUE;
	}

	return paramValue;
    }

    @Override
    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	UserProfile profile = ussdSessionMgmt.getUserProfile();
	USSDCompositeValidator validator = null;
	boolean notValid = false;//CR-86

	if (multiAmtSndr.equalsIgnoreCase("")) {
		//multiAmtSndr = getSysParamValue(profile.getBusinessId(), profile.getCountryCode(), SystemPreferenceConstants.CS_MULTIPLE_AMT);
	    multiAmtSndr = getSystemPreferenceData(ussdSessionMgmt.getUserProfile(), SystemPreferenceConstants.SYS_PARAM_CS,
				SystemPreferenceConstants.CS_MULTIPLE_AMT);
	}

	validator = new USSDCompositeValidator(new USSDMultiRangeValidator(multiAmtSndr));
	try {
		//CR-86 Back Flow
		char zero = '0';
		if(StringUtils.isEmpty(userInput) || !LongValidator.getInstance().isValid(userInput) || isNotDigits(userInput.toCharArray())){
			notValid=true;
		} else if(userInput.length() > 1 && zero == userInput.charAt(0)){
			notValid=true;
		} else {
			 validator.validate(userInput);
		}

	} catch (USSDNonBlockingException e) {
		//CR-86 Back flow changes
		e.setBackFlow(true);
		e.addErrorParam(userInput);
	    e.addErrorParam(multiAmtSndr);
	    e.setErrorCode(USSDExceptions.USSD_INVALID_MULTIPLE_AMT.getUssdErrorCode());
	    throw e;
	}
	String mininimumAmtSndr = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_CS, SystemPreferenceConstants.CS_MIN_AMT);
	String maximumAmtSndr = getSystemPreferenceData(profile, SystemPreferenceConstants.SYS_PARAM_CS, SystemPreferenceConstants.CS_MAX_AMT);

	validator = new USSDCompositeValidator(new USSDMinMaxRangeValidator(mininimumAmtSndr, maximumAmtSndr));
	try {
		//CR-86 Backflow changes
		if(notValid){
			 throw new USSDNonBlockingException();
		} else {
			 validator.validate(userInput);
		}

	} catch (USSDNonBlockingException e) {
	    // LOGGER.error(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode(), e);
		//CR-86 Back flow changes
		e.setBackFlow(true);
		e.addErrorParam(userInput);
	    e.addErrorParam(mininimumAmtSndr);
	    e.addErrorParam(maximumAmtSndr);
	    e.setErrorCode(USSDExceptions.USSD_INVALID_AMOUNT_LIMIT.getUssdErrorCode());
	    throw e;
	}

    }

    //CR-86 Back flow
    private static boolean isNotDigits(char[] sequence) {
    	boolean isNotDigits = false;
    	int sequenceLength = sequence.length;
    	for (int i = 0; i < sequenceLength; i++) {
    	    if (!Character.isDigit(sequence[i])) {
    		isNotDigits = true;
    		break;
    	    }

    	}
    	return isNotDigits;
        }

    private String getSystemPreferenceData(UserProfile userProfile, String groupId, String listValueKey) throws USSDNonBlockingException {
	ListValueResServiceRequest listValReq = new ListValueResServiceRequest(userProfile.getCountryCode(), groupId, userProfile.getLanguage(),
		listValueKey);
	ListValueResByGroupServiceResponse listValueByGroup = listValueResService.findListValueResByGroupKey(listValReq);
	ListValueCacheDTO listValueCacheDTO = listValueByGroup.getListValueCacheDTOOneRow();
	if (listValueCacheDTO == null) {
	    // LOGGER.fatal("System preferences not set for" + listValReq.getListValueKey());
	    throw new USSDNonBlockingException(USSDExceptions.USSD_SYS_PREF_MISSING.getBmgCode(), USSDExceptions.USSD_SYS_PREF_MISSING
		    .getUssdErrorCode());
	}
	return listValueCacheDTO.getLabel();
    }
}

class OneTimeCashSendGetAmountComparator implements Comparator<AccountDetails>, Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    public int compare(final AccountDetails accountDetail1, final AccountDetails accountDetail2) {
	int retVal = 0;
	if (StringUtils.equalsIgnoreCase(USSDConstants.PRIMARY_INDICATOR_YES, accountDetail1.getPriInd())) {
	    retVal = -1;
	} else if (StringUtils.equalsIgnoreCase(USSDConstants.PRIMARY_INDICATOR_YES, accountDetail2.getPriInd())) {
	    retVal = 1;
	} else {
	    retVal = 0;
	}
	return retVal;
    }
}
