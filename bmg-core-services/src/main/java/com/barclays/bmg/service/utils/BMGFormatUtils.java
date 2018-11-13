package com.barclays.bmg.service.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.product.ComponentResService;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.request.ComponentResServiceRequest;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ComponentResServiceResponse;
import com.barclays.bmg.service.product.response.ListValueResServiceResponse;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;

public class BMGFormatUtils {

    private SystemParameterService systemParameterService;
    private ListValueResService listValueResService;
    private ComponentResService componentResService;
    private Map<String, String> sysMap;
    private static final String YES = "Y";
    private static final String ACCOUNT_NUMBER_SPLIT_CHAR = " ";
    private static final String DISPLAY_ORI_DEPOSIT_NO_FOR_TD = "DISPLAY_ORI_DEPOSIT_NO_FOR_TD";

    // --- Credit Card
    private static final int CREDITCARD_LENGTH = 16;
    private static final int CC_PREFIX_MASK_PATTERN_LENTH = 6;
    private static final int CC_SUBFIX_MASK_PATTERN_LENTH = 4;
    private static final String MASK_ASTERISK = "*";
    private static final int UNMASK_NUMBER = 4;

    protected String getCorrelationId(String acctNum, ResponseContext response) {
	Map<String, Object> contextMap = response.getContext().getContextMap();
	Map<String, String> correlationIdMap = (Map<String, String>) contextMap.get(AccountConstants.CORRELATION_ID_MAP);
	String corId = null;
	if (correlationIdMap != null) {
	    corId = correlationIdMap.get(acctNum);
	}
	return corId;
    }

    protected String getMaskedTDAccountNumber(TdAccountDTO tdAccount) {

	String retMaskedActNo = "";
	String returnTrail = "";
	if (tdAccount != null) {
	    retMaskedActNo = getMaskedAccountNumber(tdAccount.getBranchCode(), tdAccount.getAccountNumber());

	    loadCommonParameters();

	    String isDispOriDeposit = "";
	    if (sysMap != null) {
		isDispOriDeposit = sysMap.get(DISPLAY_ORI_DEPOSIT_NO_FOR_TD);
	    }
	    if (StringUtils.isNotEmpty(tdAccount.getDepositDTO().getDepositNumber())) {
		returnTrail += ("-" + tdAccount.getDepositDTO().getDepositNumber());
	    }
	    if (isDispOriDeposit.equalsIgnoreCase("Y")) {
		returnTrail += ("-" + tdAccount.getDepositDTO().getOriginalDepositNumber());
	    }
	}
	return (retMaskedActNo + returnTrail);

    }

    /**
     * @param branchCode
     * @param accountNo
     * @return Get the masked account number
     */
    protected String getMaskedAccountNumber(String branchCode, String accountNo) {
	loadCommonParameters();
	String updatedbranchCode = branchCode;
	String updatedaccountNo = accountNo;

	if (BMBContextHolder.getContext().getBusinessId() != null
		&& !BMBContextHolder.getContext().getBusinessId().equals(CommonConstants.AEBRB_BUSINESS_ID)) {
	    updatedaccountNo = getFormattedBranchCode(updatedbranchCode) + updatedaccountNo;
	}

	String result = null;
	if (updatedaccountNo == null || updatedaccountNo.trim().length() < 1) {
	    result = updatedaccountNo;
	} else {
	    String trimAccountNo = updatedaccountNo.trim();

	    if (!needDisplayBranchCode()) {
		trimAccountNo = getPrecedingZero(trimAccountNo);
	    }

	    boolean maskRequired = isMaskRequired();

	    if (!maskRequired) {
		return trimAccountNo;
	    }
	    String maskPattern = getMaskAccountPattern();
	    result = maskNumber(trimAccountNo, maskPattern);
	}
	return result;
    }

    /**
     * @param cardNo
     * @return
     */
    protected String getMaskedCardNumber(String cardNo) {
	loadCommonParameters();
	String result = null;
	if (cardNo == null || cardNo.trim().length() < 1) {
	    result = cardNo;
	} else {
	    String trimAccountNo = cardNo.trim();

	    if (!needDisplayBranchCode() && CREDITCARD_LENGTH != cardNo.length()) {
		trimAccountNo = getPrecedingZero(trimAccountNo);
	    }

	    /*
	     * boolean maskRequired = isMaskRequired();
	     * 
	     * if (!maskRequired) { return trimAccountNo; }
	     */

	    // String maskPattern = getMaskAccountPattern();
	    result = maskAccount(trimAccountNo);
	}
	return result;
    }

    public String maskAccount(String accountNumber) {
	String updatedaccountNumber = accountNumber;
	if (updatedaccountNumber == null) {
	    return "";
	}
	updatedaccountNumber = updatedaccountNumber.trim();
	StringBuffer masked = new StringBuffer();
	if (updatedaccountNumber.length() == CREDITCARD_LENGTH) {
	    updatedaccountNumber = updatedaccountNumber.substring(0, CC_PREFIX_MASK_PATTERN_LENTH) + "xxxxxx"
		    + updatedaccountNumber.substring(CREDITCARD_LENGTH - CC_SUBFIX_MASK_PATTERN_LENTH, CREDITCARD_LENGTH);

	    masked.append(updatedaccountNumber);
	} else {
	    if (updatedaccountNumber.length() <= 4) {
		return updatedaccountNumber;
	    }
	    for (int i = 0; i < updatedaccountNumber.length() - UNMASK_NUMBER; i++) {
		masked.append(MASK_ASTERISK);
	    }
	    masked.append(updatedaccountNumber.substring(updatedaccountNumber.length() - UNMASK_NUMBER));
	}
	return masked.toString();
    }

    /**
     * @param request
     * @param branchCode
     * @return
     */
    private String getFormattedBranchCode(String branchCode) {
	int branchCodeLength = SystemParameterConstant.DEFAULT_BRANCH_CODE_LENGTH;
	String length = sysMap.get(SystemParameterConstant.BRANCH_CODE_LENGTH);
	if (length != null) {
	    branchCodeLength = Integer.parseInt(length);
	}

	if (branchCode == null) {
	    return "";
	}
	if (branchCode.length() >= branchCodeLength) {
	    return branchCode;
	} else {
	    StringBuilder sb = new StringBuilder();
	    for (int i = branchCode.length(); i < branchCodeLength; i++) {
		sb.append("0");
	    }
	    return sb.append(branchCode).toString();
	}
    }

    /**
     * @return
     */
    private boolean needDisplayBranchCode() {

	String needDsplyBrnchCde = sysMap.get(SystemParameterConstant.DISPLAY_BRANCH_CODE_FOR_ACCOUNT);

	if (YES.equals(needDsplyBrnchCde)) {
	    return true;
	}
	return false;
    }

    /**
     * @param accountNo
     * @return
     */
    private String getPrecedingZero(String accountNo) {
	String precedingZeroNo = "";
	for (int i = 0; i < accountNo.length(); i++) {
	    String str = accountNo.substring(i, i + 1);
	    if (!"0".equals(str)) {
		precedingZeroNo = accountNo.substring(i, accountNo.length());
		break;
	    }
	}
	return precedingZeroNo;
    }

    /**
     * @return
     */
    private boolean isMaskRequired() {
	boolean maskRequired = true;
	String required = sysMap.get(SystemParameterConstant.ACCOUNT_MASKING_REQUIRED);
	if (YES.equalsIgnoreCase(required)) {
	    maskRequired = true;
	} else {
	    maskRequired = false;
	}
	return maskRequired;
    }

    /**
     * @return
     */
    private String getMaskAccountPattern() {
	String maskPattern = "";
	maskPattern = sysMap.get(SystemParameterConstant.ACCOUNT_NUMBER_MASK_PATTERN);
	return maskPattern;
    }

    /**
     * @return
     */
    private boolean isMaskSplit() {
	boolean maskSplit = false;
	String required = sysMap.get(SystemParameterConstant.MASK_ACCOUNT_SPLIT);
	if (YES.equalsIgnoreCase(required)) {
	    maskSplit = true;
	}
	return maskSplit;
    }

    /**
     * mask Number.
     * 
     * @param toMaskNumber
     *            String
     * @param maskPattern
     *            String
     * @return String
     */
    public String maskNumber(String toMaskNumber, String maskPattern) {
	loadCommonParameters();
	String updatedmaskPattern = maskPattern;
	updatedmaskPattern = constructMaskPattern(toMaskNumber, updatedmaskPattern);
	int maskPatternLength = updatedmaskPattern.length();
	char[] cArray = updatedmaskPattern.toCharArray();
	int numberLen = 0;
	int lastPosition = 0;
	for (int i = 0; i < maskPatternLength; i++) {
	    char c = cArray[i];

	    if (Character.isDigit(c)) {
		numberLen++;
		lastPosition = i;
	    }
	}

	if (toMaskNumber.length() <= numberLen) {
	    return toMaskNumber;
	}

	String maskNumber = toMaskNumber;
	// use the real length of the toMaskNumber
	int maskLength = maskPatternLength - numberLen;
	if (maskLength > toMaskNumber.length() - numberLen) {
	    maskLength = toMaskNumber.length() - numberLen;
	}

	if (lastPosition == maskPatternLength - 1) {
	    // "*******999" "XXXX0000"
	    String maskString = updatedmaskPattern.substring(0, maskLength);
	    maskNumber = maskString + toMaskNumber.substring(toMaskNumber.length() - numberLen);
	} else if (lastPosition == numberLen - 1) {
	    // "9999*******" "0000XXXX"
	    String maskString = updatedmaskPattern.substring(numberLen, numberLen + maskLength);

	    maskNumber = toMaskNumber.substring(0, numberLen) + maskString;
	}

	boolean maskSplit = isMaskSplit();

	if (maskSplit) {
	    char[] arry = maskNumber.toCharArray();
	    StringBuffer buf = new StringBuffer();
	    for (int i = 0; i < arry.length; i++) {
		buf.append(arry[i]);
		if ((i + 1) % 4 == 0 && i != arry.length - 1) {
		    buf.append(ACCOUNT_NUMBER_SPLIT_CHAR);
		}
	    }
	    return buf.toString();
	}

	return maskNumber;
    }

    /**
     * to construct the new maskPattern which has the same length as the toMaskNumber.
     * 
     * @param toMaskNumber
     * @param maskPattern
     */
    public String constructMaskPattern(String toMaskNumber, String maskPattern) {
	// maskPattern
	// "xxxxxxxxxxxxxxxxxxxxx0000" "xxxx00000000000000000000"
	// "0000xxxxxxxxxxxxxxxxx" "00000000000000000xxxx"
	// toMaskNumber
	// "1234567890"
	String updatedmaskPattern = maskPattern;
	int maskPatternLength = updatedmaskPattern.length();
	if (maskPatternLength < toMaskNumber.length()) {
	    return updatedmaskPattern;
	}

	char[] cArray = updatedmaskPattern.toCharArray();
	int numLength = 0;
	int numLastPosition = 0;
	StringBuffer numBuffer = new StringBuffer();
	StringBuffer maskBuffer = new StringBuffer();
	for (int i = 0; i < maskPatternLength; i++) {
	    char c = cArray[i];

	    if (Character.isDigit(c)) {
		numLength++;
		numLastPosition = i;
		numBuffer.append(c);
	    } else {
		maskBuffer.append(c);
	    }

	}

	int maskLength = maskPatternLength - numLength;
	if (numLastPosition == numLength - 1) {
	    // "0000xxxxxxxxxxxxxxxxx" "00000000000000000xxxx"
	    if (maskLength > numLength) {
		// "0000xxxxxxxxxxxxxxxxx"
		updatedmaskPattern = updatedmaskPattern.substring(0, toMaskNumber.length());
		return updatedmaskPattern;
	    } else {
		// "00000000000000000xxxx"
		if (toMaskNumber.length() < maskLength) {
		    return maskBuffer.substring(0, toMaskNumber.length());
		}
		String subNumber = numBuffer.substring(0, toMaskNumber.length() - maskLength);

		return subNumber + maskBuffer.toString();
	    }

	}
	if (numLastPosition == maskPatternLength - 1) {
	    // "xxxxxxxxxxxxxxxxxxxxx0000" "xxxx00000000000000000000"
	    if (maskLength > numLength) {
		// "xxxxxxxxxxxxxxxxxxxxx0000"
		if (toMaskNumber.length() < numLength) {
		    return numBuffer.substring(0, toMaskNumber.length());
		}
		String subMask = maskBuffer.substring(0, toMaskNumber.length() - numLength);

		return subMask + numBuffer.toString();
	    } else if (maskLength < numLength) {
		// "xxxx00000000000000000000"
		updatedmaskPattern = updatedmaskPattern.substring(0, toMaskNumber.length());
		return updatedmaskPattern;
	    }

	}

	return updatedmaskPattern;

    }

    /**
     * @param activities
     */
    protected void loadCommonParameters() {

	SystemParameterDTO systemParameterDTO = new SystemParameterDTO();

	systemParameterDTO.setBusinessId(BMBContextHolder.getContext().getBusinessId());
	systemParameterDTO.setSystemId(BMBContextHolder.getContext().getSystemId());

	SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
	systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);

	SystemParameterListServiceResponse systemParameterListServiceResponse;
	List<SystemParameterDTO> systemParameterDTOList;
	Map<String, String> systemParameterMap = new HashMap<String, String>();

	systemParameterServiceRequest.getSystemParameterDTO().setActivityId(SystemParameterConstant.COMMON_ACTIVITY_ID);
	systemParameterListServiceResponse = systemParameterService.getSysParamByActivityId(systemParameterServiceRequest);
	systemParameterDTOList = systemParameterListServiceResponse.getSystemParameterDTOList();

	for (SystemParameterDTO eachSPDto : systemParameterDTOList) {
	    systemParameterMap.put(eachSPDto.getParameterId(), eachSPDto.getParameterValue());
	}

	sysMap = systemParameterMap;

    }

    /**
     * @param response
     */
    protected String getSystemParam(Context context, String paramId) {

	SystemParameterDTO systemParameterDTO = new SystemParameterDTO();

	systemParameterDTO.setBusinessId(context.getBusinessId());
	systemParameterDTO.setSystemId(context.getSystemId());
	systemParameterDTO.setParameterId(paramId);
	systemParameterDTO.setActivityId("COMMON");

	SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
	systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);
	SystemParameterServiceResponse systemParameterServiceResponse = systemParameterService.getSystemParameter(systemParameterServiceRequest);
	return systemParameterServiceResponse.getSystemParameterDTO().getParameterValue();

    }

    protected String getListValueLabel(Context context, String group, String key) {
	ListValueResServiceRequest request = new ListValueResServiceRequest();
	request.setContext(context);
	request.setGroup(group);
	request.setListValueKey(key);
	ListValueResServiceResponse response = listValueResService.getListValueRes(request);

	return response.getListValueLabel();
    }

    protected String getComponentLable(Context context, String componentKey, String screenId) {

	String label = componentKey;
	ComponentResServiceRequest request = new ComponentResServiceRequest();
	request.setContext(context);
	request.setComponentKey(componentKey);
	request.setScreenId(screenId);
	ComponentResServiceResponse response = componentResService.getComponentResCache(request);
	if (response.getComponentResCacheDTO() != null) {
	    label = response.getComponentResCacheDTO().getLabelValue();
	}
	return label;
    }

    /**
     * Returns current date in dd/MM/yyyy format
     * 
     * @return
     */
    public static String getFormattedTodaysDate() {
	String dateFormat = "dd/MM/yyyy";

	SimpleDateFormat smf = new SimpleDateFormat(dateFormat);

	return smf.format(new Date());
    }

    public SystemParameterService getSystemParameterService() {
	return systemParameterService;
    }

    public void setSystemParameterService(SystemParameterService systemParameterService) {
	this.systemParameterService = systemParameterService;
    }

    public ListValueResService getListValueResService() {
	return listValueResService;
    }

    public void setListValueResService(ListValueResService listValueResService) {
	this.listValueResService = listValueResService;
    }

    public ComponentResService getComponentResService() {
	return componentResService;
    }

    public void setComponentResService(ComponentResService componentResService) {
	this.componentResService = componentResService;
    }

}
