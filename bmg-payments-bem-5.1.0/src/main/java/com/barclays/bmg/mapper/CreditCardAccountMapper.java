package com.barclays.bmg.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.barclays.bem.CreditCardAccount.CreditCardAccount;
import com.barclays.bem.CreditCardBasic.CreditCardBasic;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardDTO;
import com.barclays.bmg.type.Currency;
import com.barclays.bmg.utils.ConvertUtils;

public class CreditCardAccountMapper {

    /* This method was used in PRIME - No changes done in existing method */
    public List<CreditCardAccountDTO> mapCollection(

    CreditCardAccount[] sourceObject) {

	List<CreditCardAccountDTO> creditCardAccountDTOList = new ArrayList<CreditCardAccountDTO>();
	for (CreditCardAccount cas : sourceObject) {
	    creditCardAccountDTOList.add(mapBean(cas, null));
	}
	return creditCardAccountDTOList;
    }

    /* This method was used in PRIME - No changes done in existing method */
    public CreditCardAccountDTO mapBean(CreditCardAccount sourceObject, CreditCardAccountDTO destiObject) {
	CreditCardAccountDTO result = null;
	if (destiObject == null) {
	    result = new CreditCardAccountDTO();
	} else {
	    result = destiObject;
	}

	// if
	// (!ConvertUtils.checkAccountStatus(sourceObject.getAccountLifecycleStatusCode())){
	// return null;
	// }
	result.setCurrency(sourceObject.getAccountCurrencyCode());
	// result.setCreditLimit(ConvertUtils.convertAmount(sourceObject.getTotalCreditLimitAmount()));
	// result.setAvailableCreditLimit(ConvertUtils.convertAmount(sourceObject.getAvailableCreditLimitAmount()));
	// --- CHANGES-DONE-FOR-UAE-3.x-ISSUE-NO-SUFFICIENT-BALANCE
	result.setAvailableBalance(ConvertUtils.convertAmount(sourceObject.getAvailableCreditLimitAmount()));

	result.setAccountNumber(sourceObject.getAccountNumber());

	if (null != sourceObject.getAccountCurrentBalance()) {
	    result.setCurrentBalance(ConvertUtils.convertAmount(sourceObject.getAccountCurrentBalance().getAccountCCYBalance()));
	} else {
	    result.setCurrentBalance(new BigDecimal(0));
	}

	// result.setDesc(sourceObject.getDescription());
	result.setProductCode(sourceObject.getProduct().getProductCode());
	result.setAccountStatus(sourceObject.getAccountLifecycleStatusCode());
	// result.setPaymentDueDate(ConvertUtils.convertDate(sourceObject.getPaymentDueDate()));
	// result.setOutstandingBalance(ConvertUtils.convertAmount(sourceObject.getOutstandingBalanceAmount()));
	// result.setMinPaymentAmount(ConvertUtils.convertAmount(sourceObject.getMinimumDueAmount()));

	CreditCardDTO primary = new CreditCardDTO();
	primary.setCreditCardAccount(result);
	result.setPrimary(primary);
	result.setSupplementaries(new ArrayList<CreditCardDTO>());
	if (sourceObject.getCreditCardInfo() != null && sourceObject.getCreditCardInfo().length > 0) {
	    for (CreditCardBasic creditCardInfo : sourceObject.getCreditCardInfo()) {
		CreditCardDTO creditCardDTO = new CreditCardDTO();
		creditCardDTO.setCreditCardAccount(result);
		creditCardDTO.setCardNumber(creditCardInfo.getCreditCardNumber());
		creditCardDTO.setCardStatus(creditCardInfo.getCreditCardLifeCycleStatusCode());
		creditCardDTO.setCardHolder(creditCardInfo.getEmbossedNameOnCard());
		creditCardDTO.setPrimaryOrSupplementary(creditCardInfo.getCreditCardOwnershipTypeCode());

		if (CreditCardDTO.PRIMARY.equals(creditCardDTO.getPrimaryOrSupplementary())) {
		    result.setPrimary(creditCardDTO);
		    if (creditCardInfo.getCreditCardProductInfo() != null) {
			result.setProductCode(creditCardInfo.getCreditCardProductInfo().getProductName());
		    }
		} else {
		    result.addSupplementary(creditCardDTO);
		}
	    }
	}

	return result;
    }

    // PRIME-VPLUS-MERGE - Added newly code only for PRIME & VPLUS, instead of
    // making changes in existing

    // add for merge visionplus code with prime code
    public List<CreditCardAccountDTO> mapCollectionVision(CreditCardAccount[] sourceObject) {

	List<CreditCardAccountDTO> creditCardAccountDTOList = new ArrayList<CreditCardAccountDTO>();
	for (CreditCardAccount cas : sourceObject) {
	    creditCardAccountDTOList.add(mapBeanVision(cas, null));
	}
	return objectDataMapping(creditCardAccountDTOList);// Added only for

	// VPlus suport -
	// creditCardAccountDTOList;
    }

    // add for merge visionplus code with prime code
    public List<CreditCardAccountDTO> mapCollectionPrime(CreditCardAccount[] sourceObject, String primary) {

	List<CreditCardAccountDTO> creditCardAccountDTOList = new ArrayList<CreditCardAccountDTO>();
	for (CreditCardAccount cas : sourceObject) {
	    creditCardAccountDTOList.add(mapBeanPrime(cas, null, primary));
	}
	return creditCardAccountDTOList;
    }

    // Method to support VPlus only
    public CreditCardAccountDTO mapBeanVision(CreditCardAccount sourceObject, CreditCardAccountDTO destiObject) {
	CreditCardAccountDTO result = null;
	if (destiObject == null) {
	    result = new CreditCardAccountDTO();
	} else {
	    result = destiObject;
	}

	// if
	// (!ConvertUtils.checkAccountStatus(sourceObject.getAccountLifecycleStatusCode())){
	// return null;
	// }
	result.setCurrency(sourceObject.getAccountCurrencyCode());
	result.setCurrencyCode(new Currency(sourceObject.getAccountCurrencyCode()));

	result.setCreditLimit(ConvertUtils.convertAmount(sourceObject.getTotalCreditLimitAmount()));
	// result.setAvailableCreditLimit(ConvertUtils.convertAmount(sourceObject.getAvailableCreditLimitAmount()));
	result.setCustAvailableLimit(ConvertUtils.convertAmount(sourceObject.getCustAvailableLimit()));

	result.setAvailableCreditLimit(ConvertUtils.convertAmount(sourceObject.getAvailableCreditLimitAmount()));

	// result.setAvailableCashLimit(ConvertUtils.convertPositiveAmount(sourceObject.getAvaliableCashLimit()));
	result.setCustAvailableCashLimit(ConvertUtils.convertAmount(sourceObject.getCustAvailableCashLimit()));

	result.setAvailableCashLimit(ConvertUtils.convertAmount(sourceObject.getAvaliableCashLimit()));

	result.setAccountStatus(sourceObject.getAccountLifecycleStatusCode());
	result.setAccountNumber(sourceObject.getAccountNumber());

	// Set AccountBlockCode1
	result.setAccountBlockCode1(sourceObject.getAccountBlockCode1());
	// if (sourceObject.getAccountBlockCode1() != null && sourceObject.getCreditCardAccountLogoCode() != null
	// && sourceObject.getCreditCardAccountOrgCode() != null) {
	// result.setAccountBlockCode1(sourceObject.getCreditCardAccountOrgCode().trim() + sourceObject.getCreditCardAccountLogoCode().trim()
	// + sourceObject.getAccountBlockCode1().trim());
	// } else {
	// result.setAccountBlockCode1(sourceObject.getAccountBlockCode1());
	// }

	// Set AccountBlockCode2
	result.setAccountBlockCode2(sourceObject.getAccountBlockCode2());
	// if (sourceObject.getAccountBlockCode2() != null && sourceObject.getCreditCardAccountLogoCode() != null
	// && sourceObject.getCreditCardAccountOrgCode() != null) {
	// result.setAccountBlockCode2(sourceObject.getCreditCardAccountOrgCode().trim() + sourceObject.getCreditCardAccountLogoCode().trim()
	// + sourceObject.getAccountBlockCode2().trim());
	// } else {
	// result.setAccountBlockCode2(sourceObject.getAccountBlockCode2());
	// }

	result.setAvailableBalance(ConvertUtils.convertAmount(sourceObject.getAvailableCreditLimitAmount()));
	if (null != sourceObject.getAccountCurrentBalance()) {
	    result.setCurrentBalance(ConvertUtils.convertAmount(sourceObject.getAccountCurrentBalance().getAccountCCYBalance()));
	} else {
	    result.setCurrentBalance(new BigDecimal(0));
	}

	result.setProductCode(sourceObject.getProduct().getProductName());
	if (sourceObject.getCreditCardAccountLogoCode() != null && sourceObject.getCreditCardAccountOrgCode() != null) {
	    result.setProductPECode(sourceObject.getCreditCardAccountOrgCode().trim() + sourceObject.getCreditCardAccountLogoCode().trim());
	} else {
	    result.setProductPECode(sourceObject.getProduct().getProductCode());
	}

	result.setProductName(sourceObject.getProduct().getProductDescription());
	result.setOrg(sourceObject.getCreditCardAccountOrgCode());
	result.setLogo(sourceObject.getCreditCardAccountLogoCode());

	result.setCashLimit(ConvertUtils.convertPositiveAmount(sourceObject.getTotalCashLimit()));

	result.setTotalCashLimit(ConvertUtils.convertAmount(sourceObject.getTotalCashLimit()));

	result.setPaymentDueDate(ConvertUtils.convertDate(sourceObject.getPaymentDueDate()));
	result.setLastPaymentDate(ConvertUtils.convertDate(sourceObject.getLastStatementDate()));
	// Added newly but never used later...
	result.setLastPaymentAmount(ConvertUtils.convertAmount(sourceObject.getLastPaymentAmount()));
	// Enable outStandingBal parameter to set with new param 'LastPyamentAmount' for V+; as it is being used across payments
	// result.setOutstandingBalance(ConvertUtils.convertAmount(sourceObject.getLastPaymentAmount())); umesh commented

	//result.setOutstandingBalance(ConvertUtils.convertAmount(sourceObject.getOutstandingBalanceAmount()));
	//Changes for creditcardOutStanding balance Map to amount over due
	result.setOutstandingBalance(ConvertUtils.convertAmount(sourceObject.getAmountOverDue()));
	//Kadikope Change- setting outStandingBalanceAmount in newly made field.
	result.setKadiKopeOutstandingBalanceAmount(ConvertUtils.convertAmount(sourceObject.getOutstandingBalanceAmount()));

	result.setMinPaymentAmount(ConvertUtils.convertAmount(sourceObject.getMinimumDueAmount()));
	// #1099 Changes- Amount Over Due = 'Total Payment Due Amt - Current
	// Payment Due Amount'
	Double TotalPaymentDueAmt = 0d;
	Double CurrentPayDueAmt = 0d;
	Double AmountOverDue = 0d;

	if (sourceObject.getTotalPayamentDueAmount() != null) {
	    TotalPaymentDueAmt = sourceObject.getTotalPayamentDueAmount();
	}
	if (sourceObject.getCurrentPaymentDueAmount() != null) {
	    CurrentPayDueAmt = sourceObject.getCurrentPaymentDueAmount();
	}

	AmountOverDue = TotalPaymentDueAmt - CurrentPayDueAmt;
	// Double AmtOverDue = new Double(AmountOverDue);

	result.setAmountOverDue(ConvertUtils.convertAmount(AmountOverDue));

	// BigDecimal AmountOverDue = sourceObject.getTotalPayamentDueAmount() -
	// sourceObject.getCurrentPaymentDueAmount();

	// result.setAmountOverDue(ConvertUtils.convertAmount(sourceObject.getAmountOverDue()));
	result.setLastBilledDate(ConvertUtils.convertDate(sourceObject.getLastStatementDate()));

	CreditCardDTO primary = new CreditCardDTO();
	primary.setCreditCardAccount(result);
	result.setPrimary(primary);
	result.setSupplementaries(new ArrayList<CreditCardDTO>());
	if (sourceObject.getCreditCardInfo() != null && sourceObject.getCreditCardInfo().length > 0) {
	    for (CreditCardBasic creditCardInfo : sourceObject.getCreditCardInfo()) {
		CreditCardDTO creditCardDTO = new CreditCardDTO();
		creditCardDTO.setCreditCardAccount(result);
		creditCardDTO.setCardNumber(creditCardInfo.getCreditCardNumber());
		creditCardDTO.setCardStatus(creditCardInfo.getCreditCardLifeCycleStatusCode());
		creditCardDTO.setCardHolder(creditCardInfo.getEmbossedNameOnCard());
		creditCardDTO.setPrimaryOrSupplementary(creditCardInfo.getCreditCardOwnershipTypeCode());

		creditCardDTO.setCreditCardOrgCode(creditCardInfo.getCreditCardOrgCode());
		creditCardDTO.setCreditCardSequenceNumber(creditCardInfo.getCreditCardSequenceNumber());

		/*
		 * if (creditCardInfo.getCreditCardBlockCode() != null && sourceObject.getCreditCardAccountLogoCode() != null &&
		 * sourceObject.getCreditCardAccountOrgCode() != null) {
		 * creditCardDTO.setCreditCardBlockCode(sourceObject.getCreditCardAccountOrgCode().trim() +
		 * sourceObject.getCreditCardAccountLogoCode().trim() + creditCardInfo.getCreditCardBlockCode().trim()); } else {
		 * creditCardDTO.setCreditCardBlockCode(creditCardInfo.getCreditCardBlockCode()); }
		 */

		if (creditCardInfo.getCreditCardBlockCode() != null) {
		    creditCardDTO.setCreditCardBlockCode(creditCardInfo.getCreditCardBlockCode().trim());
		}
		String cardType = creditCardDTO.getPrimaryOrSupplementary();

		if (cardType.equalsIgnoreCase(CreditCardDTO.PRIMARY)) {
		    result.setPrimary(creditCardDTO);
		} else {
		    result.addSupplementary(creditCardDTO);
		}
		// Cards Migration
		if (null != creditCardInfo.getCardExpiryDate()) {
			result.setCardExpireDate(creditCardInfo.getCardExpiryDate().getTime());
		}
	    }
	}
	return result;
    }

    // Method to support PRIME only
    public CreditCardAccountDTO mapBeanPrime(CreditCardAccount sourceObject, CreditCardAccountDTO destiObject, String primaryVal) {
	CreditCardAccountDTO result = null;
	if (destiObject == null) {
	    result = new CreditCardAccountDTO();
	} else {
	    result = destiObject;
	}

	// if
	// (!ConvertUtils.checkAccountStatus(sourceObject.getAccountLifecycleStatusCode())){
	// return null;
	// }
	result.setCurrency(sourceObject.getAccountCurrencyCode());
	// result.setCreditLimit(ConvertUtils.convertAmount(sourceObject.getTotalCreditLimitAmount()));
	// result.setAvailableCreditLimit(ConvertUtils.convertAmount(sourceObject.getAvailableCreditLimitAmount()));
	// --- CHANGES-DONE-FOR-UAE-3.x-ISSUE-NO-SUFFICIENT-BALANCE
	result.setAvailableBalance(ConvertUtils.convertAmount(sourceObject.getAvailableCreditLimitAmount()));

	result.setAccountNumber(sourceObject.getAccountNumber());

	if (null != sourceObject.getAccountCurrentBalance()) {
	    result.setCurrentBalance(ConvertUtils.convertAmount(sourceObject.getAccountCurrentBalance().getAccountCCYBalance()));
	} else {
	    result.setCurrentBalance(new BigDecimal(0));
	}

	// result.setDesc(sourceObject.getDescription());
	result.setProductCode(sourceObject.getProduct().getProductCode());
	result.setAccountStatus(sourceObject.getAccountLifecycleStatusCode());
	// result.setPaymentDueDate(ConvertUtils.convertDate(sourceObject.getPaymentDueDate()));
	// result.setOutstandingBalance(ConvertUtils.convertAmount(sourceObject.getOutstandingBalanceAmount()));
	// result.setMinPaymentAmount(ConvertUtils.convertAmount(sourceObject.getMinimumDueAmount()));

	CreditCardDTO primary = new CreditCardDTO();
	primary.setCreditCardAccount(result);
	result.setPrimary(primary);
	result.setSupplementaries(new ArrayList<CreditCardDTO>());
	if (sourceObject.getCreditCardInfo() != null && sourceObject.getCreditCardInfo().length > 0) {
	    for (CreditCardBasic creditCardInfo : sourceObject.getCreditCardInfo()) {
		CreditCardDTO creditCardDTO = new CreditCardDTO();
		creditCardDTO.setCreditCardAccount(result);
		creditCardDTO.setCardNumber(creditCardInfo.getCreditCardNumber());
		creditCardDTO.setCardStatus(creditCardInfo.getCreditCardLifeCycleStatusCode());
		creditCardDTO.setCardHolder(creditCardInfo.getEmbossedNameOnCard());
		creditCardDTO.setPrimaryOrSupplementary(creditCardInfo.getCreditCardOwnershipTypeCode());
		// Cards Migration
		if (null != creditCardInfo.getCreditCardExpiryDate())
			creditCardDTO.setCardExpireDate(creditCardInfo.getCardExpiryDate().getTime());

		if (primaryVal.equals(creditCardDTO.getPrimaryOrSupplementary())) {
		    result.setPrimary(creditCardDTO);
		    if (creditCardInfo.getCreditCardProductInfo() != null) {
			result.setProductCode(creditCardInfo.getCreditCardProductInfo().getProductName());
		    }
		} else {
		    result.addSupplementary(creditCardDTO);
		}
	    }
	}

	return result;
    }

    // Method to support VPlus only
    public List<CreditCardAccountDTO> objectDataMapping(List<CreditCardAccountDTO> creditCardAccountDTOList) {

	String prev = null;
	String curr = null;
	int primaryPos = -1;

	CreditCardAccountDTO prevDTO = null;
	CreditCardAccountDTO currDTO = null;
	CreditCardAccountDTO temp1 = null;

	List<CreditCardAccountDTO> finalDTO = new ArrayList<CreditCardAccountDTO>();

	Collections.sort(creditCardAccountDTOList);

	for (int i = 0; i < creditCardAccountDTOList.size(); i++) {

	    curr = creditCardAccountDTOList.get(i).getAccountNumber();

	    // start
	    if (temp1 != null) {

		if (temp1.getAccountNumber().equalsIgnoreCase(curr)) {

		} else {

		    temp1 = null;
		}

	    }

	    // end

	    if (prev == null) {

		prevDTO = creditCardAccountDTOList.get(i);

		if (prevDTO.getPrimary() != null && prevDTO.getPrimary().getPrimaryOrSupplementary() != null
			&& prevDTO.getPrimary().getPrimaryOrSupplementary().equals("P")) {
		    primaryPos = -1;

		    prevDTO.setSupplementaries(new ArrayList<CreditCardDTO>());
		    finalDTO.add(prevDTO); //
		    temp1 = prevDTO;
		    prev = curr;
		    primaryPos++;

		} else {

		    temp1 = prevDTO;

		}

	    } else {

		currDTO = creditCardAccountDTOList.get(i);

		if (currDTO.getPrimary() != null && currDTO.getPrimary().getPrimaryOrSupplementary() != null
			&& currDTO.getPrimary().getPrimaryOrSupplementary().equals("P")) {

		    if (temp1 != null) {

			if (temp1.getAccountNumber().equalsIgnoreCase(curr)) {

			    currDTO.setSupplementaries(temp1.getSupplementaries());
			    finalDTO.add(currDTO);
			} else {

			    finalDTO.add(currDTO);
			}

			// temp1=null;
		    } else {

			primaryPos = -1;
			currDTO.setSupplementaries(new ArrayList<CreditCardDTO>());
			finalDTO.add(currDTO);
			temp1 = currDTO;
			prev = curr;
			primaryPos++;

		    }

		} else {

		    if (temp1 != null) {

			if (temp1.getAccountNumber().equalsIgnoreCase(curr)) {

			    // start
			    if (temp1.getPrimary() != null && temp1.getPrimary().getPrimaryOrSupplementary() != null
				    && temp1.getPrimary().getPrimaryOrSupplementary().equals("P")) {

				// ((CreditCardAccountDTO)finalDTO.get(primaryPos)).setSupplementaries(currDTO.getSupplementaries());

				int lastAddPos = finalDTO.size() - 1;
				(finalDTO.get(lastAddPos)).addSupplementary(currDTO.getSupplementaries().get(0));

				temp1 = currDTO;

			    } else {
				int lastAddPos = finalDTO.size() - 1;
				(finalDTO.get(lastAddPos)).addSupplementary(currDTO.getSupplementaries().get(0));
				temp1 = currDTO;

			    }

			    // end

			} else {

			    temp1 = null;
			}

		    }

		}

	    }

	}
	return finalDTO;
    }

    /* This method is not in use - Delete later on */
    /*
     * private Double getMinimumAmount(Double amount1, Double amount2) { Double minAmt = 0d;
     *
     * if (amount1 < amount2) {
     *
     * minAmt = amount1;
     *
     * } else {
     *
     * minAmt = amount2; } return minAmt; }
     */
}