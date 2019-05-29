package com.barclays.bmg.operation.accountservices;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.dao.core.proxy.util.MaskingMode;
import com.barclays.bmg.dao.core.proxy.util.MaskingRule;
import com.barclays.bmg.dao.core.proxy.util.MaskingRuleImpl;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.accountdetails.response.AccountAdditionalInfoOps;
import com.barclays.bmg.operation.accountdetails.response.AccountAdditionalInfoSer;
import com.barclays.bmg.operation.accountdetails.response.AccountOps;
import com.barclays.bmg.operation.accountdetails.response.AccountSer;
import com.barclays.bmg.service.accounts.AllGroupWalletAccountService;
import com.barclays.bmg.service.accounts.request.AllGroupWalletAccountOperationRequest;
import com.barclays.bmg.service.accounts.request.AllGroupWalletAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllGroupWalletAccountOperationResponse;
import com.barclays.bmg.service.accounts.response.AllGroupWalletAccountServiceResponse;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;

public class RetrieveGroupWalletAccountListOperation extends BMBCommonOperation {
	AllGroupWalletAccountService allGroupWalletAccountService;
	private static Map<String, String> sysMap;

	public AllGroupWalletAccountService getAllGroupWalletAccountService(
			AllGroupWalletAccountServiceRequest request) {
		return allGroupWalletAccountService;
	}

	public void setAllGroupWalletAccountService(
			AllGroupWalletAccountService allGroupWalletAccountService) {
		this.allGroupWalletAccountService = allGroupWalletAccountService;
	}

	public AllGroupWalletAccountOperationResponse getAllGroupWalletAccountList(
			AllGroupWalletAccountOperationRequest allGroupWalletAccountOperationRequest) {
		// TODO Auto-generated method stub
		AllGroupWalletAccountServiceRequest allGroupWalletAccountServiceRequest = new AllGroupWalletAccountServiceRequest();
		allGroupWalletAccountServiceRequest
				.setAccountNumber(allGroupWalletAccountOperationRequest
						.getAccountNumber());
		allGroupWalletAccountServiceRequest
				.setActionCode(allGroupWalletAccountOperationRequest
						.getActionCode());
		allGroupWalletAccountServiceRequest
				.setActionCodeValue(allGroupWalletAccountOperationRequest
						.getActionCodeValue());
		allGroupWalletAccountServiceRequest
				.setCustomerID(allGroupWalletAccountOperationRequest
						.getCustomerID());
		allGroupWalletAccountServiceRequest
				.setCustomerType(allGroupWalletAccountOperationRequest
						.getCustomerType());
		loadParameters(allGroupWalletAccountOperationRequest.getContext(),
				ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
		allGroupWalletAccountServiceRequest
				.setContext(allGroupWalletAccountOperationRequest.getContext());

		AllGroupWalletAccountServiceResponse allGroupWalletAccountServiceResponse = allGroupWalletAccountService
				.retrieveAllGroupWalletAccount(allGroupWalletAccountServiceRequest);

		AllGroupWalletAccountOperationResponse allGroupWalletAccountOperationResponse = new AllGroupWalletAccountOperationResponse();

		if (allGroupWalletAccountServiceResponse != null) {
			if (allGroupWalletAccountServiceResponse.isSuccess()) {
				List<AccountSer> aListSer = allGroupWalletAccountServiceResponse
						.getNonPersonalCustAcctList();
				List<AccountOps> aListOps = new LinkedList<AccountOps>();

				if (aListSer != null && aListSer.size() > 0) {
					for (AccountSer aser : aListSer) {
						AccountOps ops = new AccountOps();
						AccountAdditionalInfoOps aops = new AccountAdditionalInfoOps();
						AccountAdditionalInfoSer aaiser = aser
								.getAccountAdditionalInfo();

						aops.setAccountId(aaiser.getAccountId());
						aops.setMaskedAccountId(partialMask(aaiser.getAccountId()));
						aops.setAccountTitle(aaiser.getAccountTitle());
						aops.setAuthLevel(aaiser.getAuthLevel());
						aops.setAvailableBalance(aaiser.getAvailableBalance());
						aops.setBranchCode(aaiser.getBranchCode());
						aops.setBranchName(aaiser.getBranchName());
						aops.setCurrencyCode(aaiser.getCurrencyCode());
						aops
								.setCurrencyShortName(aaiser
										.getCurrencyShortName());
						aops.setCurrentBalance(aaiser.getCurrentBalance());
						aops.setCurrentStatus(aaiser.getCurrentStatus());
						aops.setCustomerId(aaiser.getCustomerId());
						aops.setCustomerRelationship(aaiser
								.getCustomerRelationship());
						aops.setProductCode(aaiser.getProductCode());
						aops.setProductName(aaiser.getProductName());

						ops.setAccountAdditionalInfo(aops);
						aListOps.add(ops);
					}
				}
				allGroupWalletAccountOperationResponse
						.setNonPersonalCustAcctList(aListOps);
			}
		}
		return allGroupWalletAccountOperationResponse;
	}

	String partialMask(String text) {
		int unmaskNumber = 4;
    	String updatedtext = text;
    	updatedtext = updatedtext.trim();
        if (updatedtext.length() <= unmaskNumber) {
            return updatedtext;
        }
        StringBuilder masked = new StringBuilder();
        for (int i = 0; i < updatedtext.length() - unmaskNumber; i++) {
            masked.append("x");
        }
        masked.append(updatedtext.substring(updatedtext.length() - unmaskNumber));
        return masked.toString();
    }

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
	public String maskNumber(String toMaskNumber, String maskPattern) {
		String ACCOUNT_NUMBER_SPLIT_CHAR = " ";
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
	 private boolean isMaskSplit() {
		 String YES = "Y";
			boolean maskSplit = false;
			String required = sysMap.get(SystemParameterConstant.MASK_ACCOUNT_SPLIT);
			if (YES.equalsIgnoreCase(required)) {
			    maskSplit = true;
			}
			return maskSplit;
		    }
}
