package com.barclays.bmg.operation.accountdetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.Renewal;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.accountdetails.request.TDAccountDetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.TDAccountDetailsOperationResponse;
import com.barclays.bmg.service.accountdetails.TDDetailsService;
import com.barclays.bmg.service.accountdetails.request.TDDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.TDDetailsServiceResponse;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.ProductEligibilityService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.request.ProductEligibilityServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResServiceResponse;
import com.barclays.bmg.service.product.response.ProductEligibilityListServiceResponse;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;
import com.barclays.bmg.type.Currency;

/**
 * @author E20026340
 * 
 */
public class TDAccountDetailsOperation extends BMBCommonOperation {

    private AllAccountService allAccountService;
    private ProductEligibilityService productEligibilityService;
    private TDDetailsService tdDetailsService;
    private ListValueResService listValueResService;

    private static Map<String, String> sysMap;
    private static final String YES = "Y";
    private static final String ACCOUNT_NUMBER_SPLIT_CHAR = " ";
    private static final String DISPLAY_ORI_DEPOSIT_NO_FOR_TD = "DISPLAY_ORI_DEPOSIT_NO_FOR_TD";

    /**
     * 1. Retrieve All Account List. 2. Check whether the Account is legible. 3. Get TD Account Details. 3. Set the maturity Instructions.
     * 
     * @param request
     * @return
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_RETRIEVE_TD_DETAILS", stepId = "1", activityType = "auditTDDetail")
    public TDAccountDetailsOperationResponse retreiveTDAccountDetails(TDAccountDetailsOperationRequest request) {

	Context context = request.getContext();
	TDAccountDetailsOperationResponse response = new TDAccountDetailsOperationResponse();
	response.setContext(context);

	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, ActivityConstant.SEC_ONLN_REG, context.getActivityId());
	List<? extends CustomerAccountDTO> allAccountList = getAllAccountList(request);

	CustomerAccountDTO custAcct = getSelectedTDAccount(request.getAcctNo(), allAccountList);

	if (custAcct != null) {
	    List<? extends CustomerAccountDTO> srcActLst = getSourceAccountsList(allAccountList, request);

	    if (checkIfAcctEligible(srcActLst, custAcct)) {
		TdAccountDTO tdAccountDTO = getTDAccountDetails(custAcct, request, allAccountList, response);
		if (tdAccountDTO != null) {
		    setMaturityInstruction(tdAccountDTO, response, request);
		    response.setTdAccountDTO(tdAccountDTO);
		} else {
		    response.setSuccess(false);
		    response.setResCde(AccountServiceResponseCodeConstant.ACT_ACTDETAIL_NOACTAVAILABLE);
		}

	    } else {
		response.setSuccess(false);
		response.setResCde(AccountServiceResponseCodeConstant.ACT_ACTDETAIL_CANNOTACCESS);
	    }
	} else {
	    response.setSuccess(false);
	    response.setResCde(AccountServiceResponseCodeConstant.ACT_ACTDETAIL_CANNOTACCESS);
	}
	if (!response.isSuccess()) {
	    getMessage(response);
	}
	return response;
    }

    /**
     * Retrieves TDAccount details for a account
     * 
     * @param custAcct
     * @param request
     * @param allAccountList
     * @return
     */
    private TdAccountDTO getTDAccountDetails(CustomerAccountDTO custAcct, RequestContext request, List<? extends CustomerAccountDTO> allAccountList,
	    TDAccountDetailsOperationResponse response) {
	TdAccountDTO summaryAccountDTO = (TdAccountDTO) custAcct;

	TDDetailsServiceRequest tdDetailsServiceRequest = new TDDetailsServiceRequest();
	tdDetailsServiceRequest.setContext(request.getContext());
	tdDetailsServiceRequest.setCoreTDAcctDTO(summaryAccountDTO);
	TDDetailsServiceResponse tdDetailsServiceResponse = tdDetailsService.retreiveTDDetails(tdDetailsServiceRequest);
	TdAccountDTO tdAccountDTO = tdDetailsServiceResponse.getTdAccountDTO();
	if (tdAccountDTO != null) {

	    tdAccountDTO.setCurrency(summaryAccountDTO.getCurrency());
	    tdAccountDTO.setDesc(summaryAccountDTO.getDesc());

	    tdAccountDTO.getDepositDTO().setCurrencyCode(new Currency(summaryAccountDTO.getCurrency()));
	    tdAccountDTO.setAccountNumber(summaryAccountDTO.getAccountNumber());
	    // tdAccountDTO.setCurrency(summaryAccountDTO.getCurrency());
	    tdAccountDTO.setBranchCode(summaryAccountDTO.getBranchCode());
	    tdAccountDTO.getDepositDTO().setDepositNumber(summaryAccountDTO.getDepositDTO().getDepositNumber());
	    tdAccountDTO.getDepositDTO().setOriginalDepositNumber(summaryAccountDTO.getDepositDTO().getOriginalDepositNumber());
	    tdAccountDTO.setProductCode(summaryAccountDTO.getProductCode());

	    if (tdAccountDTO.getDepositDTO() != null && tdAccountDTO.getDepositDTO().getRenewal() != null
		    && tdAccountDTO.getDepositDTO().getRenewal().getTdPrincipleBeneficiaryAcct() != null
		    && tdAccountDTO.getDepositDTO().getRenewal().getTdPrincipleBeneficiaryAcct().getAccountNumber() != null) {

		tdAccountDTO.getDepositDTO().getRenewal().setTdPrincipleBeneficiaryAcct(
			getSelectedAccount(allAccountList, tdAccountDTO.getDepositDTO().getRenewal().getTdPrincipleBeneficiaryAcct()
				.getAccountNumber()));

	    }

	    if (tdAccountDTO.getDepositDTO() != null && tdAccountDTO.getDepositDTO().getRenewal() != null
		    && tdAccountDTO.getDepositDTO().getRenewal().getTdInterestRateBeneficiaryAcct() != null
		    && tdAccountDTO.getDepositDTO().getRenewal().getTdInterestRateBeneficiaryAcct().getAccountNumber() != null) {

		tdAccountDTO.getDepositDTO().getRenewal().setTdInterestRateBeneficiaryAcct(
			getSelectedAccount(allAccountList, tdAccountDTO.getDepositDTO().getRenewal().getTdInterestRateBeneficiaryAcct()
				.getAccountNumber()));

	    }

	}
	response.setResCde(tdDetailsServiceResponse.getResCde());
	return tdAccountDTO;
    }

    /**
     * Retrieve Label
     * 
     * @param request
     * @param group
     * @param key
     * @return
     */
    private String getLabel(RequestContext request, String group, String key) {

	String label = "";
	ListValueResServiceRequest listReq = new ListValueResServiceRequest();
	listReq.setContext(request.getContext());
	listReq.setGroup(group);
	listReq.setListValueKey(key);
	ListValueResServiceResponse listResp = listValueResService.getListValueRes(listReq);
	if (listResp != null) {
	    label = listResp.getListValueLabel();
	}
	return label;
    }

    /**
     * Set Maturity Instruction in TdAccountDTO
     * 
     * @param accountDTO
     * @param response
     * @param request
     */
    private void setMaturityInstruction(TdAccountDTO accountDTO, TDAccountDetailsOperationResponse response, TDAccountDetailsOperationRequest request) {

	Renewal renewal = accountDTO.getDepositDTO().getRenewal();
	String principalRenewalType = accountDTO.getDepositDTO().getRenewal().getPrincipalRenewalType();

	String principalInst = getLabel(request, "TD_PRINCIPAL_MTY_OPT_CD", principalRenewalType);

	if (principalRenewalType != null && "2".equals(principalRenewalType) || "3".equals(principalRenewalType) || "4".equals(principalRenewalType)
		|| "9".equals(principalRenewalType)) {

	    if (renewal != null && renewal.getTdPrincipleBeneficiaryAcct().getAccountNumber() != null) {

		principalInst = principalInst + " ( " + renewal.getTdPrincipleBeneficiaryAcct().getDesc() + " "
			+ getMaskedTDAccountNumber(renewal.getTdPrincipleBeneficiaryAcct()) + " "
			+ renewal.getTdPrincipleBeneficiaryAcct().getCurrency() + " ) ";
	    }
	}
	response.setPriMatInstruction(principalInst);

	String interestRenewalType = accountDTO.getDepositDTO().getRenewal().getInterestRenewalType();
	String interestInst = getLabel(request, "TD_INTEREST_MTY_OPT_CD", interestRenewalType);

	if (interestRenewalType != null && "2".equals(interestRenewalType) || "3".equals(interestRenewalType) || "4".equals(interestRenewalType)
		|| "9".equals(interestRenewalType)) {

	    if (renewal != null && renewal.getTdInterestRateBeneficiaryAcct().getAccountNumber() != null) {

		interestInst = interestInst + " ( " + renewal.getTdInterestRateBeneficiaryAcct().getDesc() + " "
			+ getMaskedTDAccountNumber(renewal.getTdInterestRateBeneficiaryAcct()) + " "
			+ renewal.getTdInterestRateBeneficiaryAcct().getCurrency() + " ) ";
	    }
	}
	response.setIntMatInstruction(interestInst);

    }

    /**
     * Retrieves All Accounts
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<? extends CustomerAccountDTO> getAllAccountList(TDAccountDetailsOperationRequest request) {
	// TODO Auto-generated method stub
	AllAccountServiceRequest allAccountServiceRequest = new AllAccountServiceRequest();
	allAccountServiceRequest.setContext(request.getContext());

	// Retrieve all account List from BEM
	AllAccountServiceResponse allAccountServiceResponse = allAccountService.retrieveAllAccount(allAccountServiceRequest);
	List<CustomerAccountDTO> accountList = (List<CustomerAccountDTO>) allAccountServiceResponse.getAccountList();

	return accountList;
    }

    /**
     * Get Account list eligible for TD Details
     * 
     * @param accountList
     * @param request
     * @return
     */
    protected List<? extends CustomerAccountDTO> getSourceAccountsList(List<? extends CustomerAccountDTO> accountList, RequestContext request) {

	ProductEligibilityServiceRequest sourceProductEligibilityServiceRequest = new ProductEligibilityServiceRequest();
	sourceProductEligibilityServiceRequest.setContext(request.getContext());
	sourceProductEligibilityServiceRequest.setActivityId(request.getContext().getActivityId());
	sourceProductEligibilityServiceRequest.setProductIndicator(CommonConstants.DEBIT_PRODUCT);
	sourceProductEligibilityServiceRequest.setAccountList(accountList);
	sourceProductEligibilityServiceRequest.setDrOrCr(CommonConstants.DEBIT_PRODUCT);

	ProductEligibilityListServiceResponse sourceProductEligibilityListServiceResponse = productEligibilityService
		.getEligibleAccounts(sourceProductEligibilityServiceRequest);

	List<? extends CustomerAccountDTO> eligibleAccounts = sourceProductEligibilityListServiceResponse.getAccountList();

	return eligibleAccounts;

    }

    /**
     * Get account from account list using account number.
     * 
     * @param acctNumber
     * @param accountList
     * @return
     */
    private CustomerAccountDTO getSelectedTDAccount(String acctDepositNumber, List<? extends CustomerAccountDTO> accountList) {
	CustomerAccountDTO selectedAcct = null;
	if (accountList != null && acctDepositNumber != null && accountList.size() > 0) {

	    for (CustomerAccountDTO account : accountList) {
		if (account instanceof TdAccountDTO) {
		    int splitIndex = acctDepositNumber.indexOf("-");
		    if (splitIndex != -1) {
			String acctNumber = acctDepositNumber.substring(0, splitIndex);
			String tdNumber = acctDepositNumber.substring(splitIndex + 1);
			TdAccountDTO tdAcct = (TdAccountDTO) account;
			if (tdAcct.getAccountNumber().equals(acctNumber)) {
			    if (tdAcct.getDepositDTO() != null) {
				if (tdAcct.getDepositDTO().getDepositNumber().equals(tdNumber)) {
				    selectedAcct = tdAcct;
				    break;
				}
			    }
			}
		    }
		}
	    }
	}
	return selectedAcct;
    }

    /**
     * Get account from account list using account number.
     * 
     * @param allAccounts
     * @param acctNo
     * @return
     */
    private CustomerAccountDTO getSelectedAccount(List<? extends CustomerAccountDTO> allAccounts, String acctNo) {
	CustomerAccountDTO selectedAccount = null;

	if (allAccounts != null && acctNo != null && !allAccounts.isEmpty()) {
	    for (CustomerAccountDTO account : allAccounts) {
		if (acctNo.equals(account.getAccountNumber())) {
		    selectedAccount = account;
		    break;
		}
	    }
	}
	return selectedAccount;
    }

    /**
     * Check if account is eligible
     * 
     * @param accountList
     * @param custAccnt
     * @return
     */
    private boolean checkIfAcctEligible(List<? extends CustomerAccountDTO> accountList, CustomerAccountDTO custAccnt) {
	if (custAccnt instanceof TdAccountDTO) {
	    for (CustomerAccountDTO account : accountList) {
		if (account.getAccountNumber().equals(custAccnt.getAccountNumber())) {
		    return true;
		}
	    }
	}

	return false;
    }

    /**
     * Mask account number TD Account number
     * 
     * @param custAcct
     * @return
     */
    protected String getMaskedTDAccountNumber(CustomerAccountDTO custAcct) {

	// TdAccountDTO tdAccount = (TdAccountDTO) custAcct;
	String retMaskedActNo = "";

	if (custAcct != null) {
	    retMaskedActNo = getMaskedAccountNumber(custAcct.getBranchCode(), custAcct.getAccountNumber());
	}

	loadCommonParameters();
	if (custAcct instanceof TdAccountDTO) {
	    TdAccountDTO tdAccount = (TdAccountDTO) custAcct;
	    String isDispOriDeposit = "";
	    if (sysMap != null) {
		isDispOriDeposit = sysMap.get(DISPLAY_ORI_DEPOSIT_NO_FOR_TD);
	    }

	    String returnTrail = "";

	    if (StringUtils.isNotEmpty(tdAccount.getDepositDTO().getDepositNumber())) {
		returnTrail += ("-" + tdAccount.getDepositDTO().getDepositNumber());
	    }
	    if (isDispOriDeposit.equalsIgnoreCase("Y")) {
		returnTrail += ("-" + tdAccount.getDepositDTO().getOriginalDepositNumber());
	    }
	    return (retMaskedActNo + returnTrail);
	}

	return (retMaskedActNo);

    }

    /**
     * Get the masked account number
     * 
     * @param branchCode
     * @param accountNo
     * @return
     */
    protected String getMaskedAccountNumber(String branchCode, String accountNo) {
	loadCommonParameters();
	String accountNo1 = accountNo;

	if (BMBContextHolder.getContext().getBusinessId() != null
		&& !BMBContextHolder.getContext().getBusinessId().equals(CommonConstants.AEBRB_BUSINESS_ID)) {
	    accountNo1 = getFormattedBranchCode(branchCode) + accountNo1;
	}

	String result = null;
	if (accountNo1 == null || accountNo1.trim().length() < 1) {
	    result = accountNo1;
	} else {
	    String trimAccountNo = accountNo1.trim();

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
     * Loads the System parameter in context
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
     * get the formated branch code.
     * 
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

    public AllAccountService getAllAccountService() {
	return allAccountService;
    }

    public void setAllAccountService(AllAccountService allAccountService) {
	this.allAccountService = allAccountService;
    }

    public ProductEligibilityService getProductEligibilityService() {
	return productEligibilityService;
    }

    public void setProductEligibilityService(ProductEligibilityService productEligibilityService) {
	this.productEligibilityService = productEligibilityService;
    }

    public TDDetailsService getTdDetailsService() {
	return tdDetailsService;
    }

    public void setTdDetailsService(TDDetailsService tdDetailsService) {
	this.tdDetailsService = tdDetailsService;
    }

    @Override
    public ListValueResService getListValueResService() {
	return listValueResService;
    }

    @Override
    public void setListValueResService(ListValueResService listValueResService) {
	this.listValueResService = listValueResService;
    }

}
