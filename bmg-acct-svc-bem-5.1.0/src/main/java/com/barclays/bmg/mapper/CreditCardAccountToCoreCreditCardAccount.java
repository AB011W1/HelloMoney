/*
 * Copyright (c) 2009 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */

package com.barclays.bmg.mapper;

import java.util.ArrayList;

import com.barclays.bem.CreditCardAccountSummary.CreditCardInfo;
import com.barclays.bem.RetrieveCreditCardAcctDetails.CreditCardAccountDetails;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardDTO;
import com.barclays.bmg.type.Currency;
import com.barclays.bmg.utils.ConvertUtils;

/* *************************** Revision History *********************************
 * Version        Author          Date                         Description
 * 0.1            Martin Sit      29 Jul 2009                  Initial version
 *
 *
 ********************************************************************************/

/**
 * TODO Class/Interface description.
 * 
 * @author Martin Sit
 */

public class CreditCardAccountToCoreCreditCardAccount {

    /**
     * @param source
     * @param dest
     * @return
     * @see com.barclays.mcfe.ssc.core.utils.BeanMapper#mapBean(java.lang.Object, java.lang.Object)
     */
    public CreditCardAccountDTO mapBean(CreditCardAccountDetails sourceObject, CreditCardAccountDTO dest) {

	CreditCardAccountDTO result = null;
	if (dest == null) {
	    result = new CreditCardAccountDTO();
	} else {
	    result = dest;
	}
	result.setCurrency(sourceObject.getCreditCardAccountSummary().getAccountCurrencyCode());
	result.setCurrencyCode(new Currency(sourceObject.getCreditCardAccountSummary().getAccountCurrencyCode()));
	result.setCreditLimit(ConvertUtils.convertPositiveAmount(sourceObject.getCreditCardAccountSummary().getTotalCreditLimitAmount()));
	result.setAvailableCreditLimit(ConvertUtils.convertAmount(sourceObject.getCreditCardAccountSummary().getAvailableCreditLimitAmount()));
	result.setAccountNumber(sourceObject.getCreditCardAccountSummary().getAccountNumber());
	result.setCurrentBalance(ConvertUtils.convertAmount(sourceObject.getCreditCardAccountSummary().getTotalUnbilledAmount()));
	// --- CHANGES-DONE-FOR-UAE-3.x-ISSUE-NO-SUFFICIENT-BALANCE
	result.setAvailableBalance(ConvertUtils.convertAmount(sourceObject.getCreditCardAccountSummary().getAvailableCreditLimitAmount()));
	// result.setDesc(sourceObject.getCreditCardAccountSummary().getDescription());
	// result.setOrg(sourceObject.getCreditCardAccountOrgCode());
	// result.setLogo(sourceObject.getCreditCardAccountLogoCode());
	result.setProductCode(sourceObject.getCreditCardAccountSummary().getProduct().getProductCode());

	result.setCashLimit(ConvertUtils.convertPositiveAmount(sourceObject.getCreditCardAccountSummary().getTotalCashLimitAmount()));
	result.setAvailableCashLimit(ConvertUtils.convertPositiveAmount(sourceObject.getCreditCardAccountSummary().getAvailableCashLimitAmount()));
	result.setLastBilledDate(ConvertUtils.convertDate(sourceObject.getCreditCardAccountSummary().getDateofLastStatement()));
	result.setLastPaymentDate(ConvertUtils.convertDate(sourceObject.getCreditCardAccountSummary().getLastPaymentAmountDate()));
	result.setLastPaymentAmount(ConvertUtils.convertAmount(sourceObject.getCreditCardAccountSummary().getLastPaymentAmount()));
	result.setPaymentDueDate(ConvertUtils.convertDate(sourceObject.getCreditCardAccountSummary().getTotalAmountDueDate()));
	result.setMinPaymentAmount(ConvertUtils.convertPositiveAmount(sourceObject.getCreditCardAccountSummary().getMinimumDueAmount()));
	result.setAmountOverDue(ConvertUtils.convertPositiveAmount(sourceObject.getCreditCardAccountSummary().getTotalAmountDue()));

	// result.setOutstandingBalance(ConvertUtils.convertAmount(sourceObject.getCreditCardAccountSummary().getCurrentOutstandingBalanceAmount()));

	result.setPrimary(new CreditCardDTO());
	// result.setLastCreditChangeDate(ConvertUtils.convertDate(sourceObject.getCreditCardAccountSummary().getLastCreditLimitDate()));
	// result.setTemporaryCreditExpireDate(ConvertUtils.convertDate(sourceObject.getCreditCardAccountSummary().getCreditCardLimit().getTemporaryLimitExpiryDate()));
	// result.setTemporaryCreditLimit(ConvertUtils.convertAmount(sourceObject.getCreditCardAccountSummary().getCreditCardLimit().getTemporaryCreditLimitAmount()));
	result.setSupplementaries(new ArrayList<CreditCardDTO>());
	// Potential Field

	if (sourceObject.getCreditCardAccountSummary().getCreditCardInfo() != null
		&& sourceObject.getCreditCardAccountSummary().getCreditCardInfo().length > 0) {
	    for (CreditCardInfo creditCardInfo : sourceObject.getCreditCardAccountSummary().getCreditCardInfo()) {
		CreditCardDTO creditCardDTO = new CreditCardDTO();
		creditCardDTO.setCardNumber(creditCardInfo.getCreditCardNumber());
		creditCardDTO.setCardStatus(creditCardInfo.getCreditCardLifeCycleStatueCode());
		creditCardDTO.setCardHolder(creditCardInfo.getEmbossedNameOnCard());
		creditCardDTO.setPrimaryOrSupplementary(creditCardInfo.getCreditCardOwnershipTypeCode());
		// Potential Field
		// creditCardDTO.setCardExpireDate(sourceObject.getCreditCardAccountSummary().get);
		if (CreditCardDTO.PRIMARY.equals(creditCardDTO.getPrimaryOrSupplementary())) {
		    result.setPrimary(creditCardDTO);
		    if (creditCardInfo.getCreditCardProductInfo() != null) {

			// result.setProductCode(creditCardInfo.getCreditCardProductInfo().getProductName());
			result.setProductCode(creditCardInfo.getCreditCardProductInfo().getProductCode());
		    }
		} else {
		    result.addSupplementary(creditCardDTO);
		}
	    }
	}
	return result;
    }

}
