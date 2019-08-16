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

import java.math.BigDecimal;

import com.barclays.bem.RetrieveCASAAcctDetails.CASAAccountDetails;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.type.Currency;
import com.barclays.bmg.utils.ConvertUtils;

/**
 * *************************** Revision History *********************************
 * Version        Author          Date                     Description
 * 0.1            Martin Sit      11 Aug 2009                  Initial version
 *
 *
 ********************************************************************************/

/**
 * TODO Class/Interface description.
 * 
 * @author Martin Sit
 */
public class CASAAccountToCoreCASAAccount {

    public CASAAccountDTO mapBean(CASAAccountDetails sourceObject, CASAAccountDTO destiObject) {
	CASAAccountDTO result = null;
	if (null == destiObject) {
	    result = new CASAAccountDTO();
	} else {
	    result = destiObject;
	}
	if (sourceObject == null) {
	    return result;
	}
	if (sourceObject.getCASAAccountSummary() != null) {
	    result.setCurrency(sourceObject.getCASAAccountSummary().getAccountCurrencyCode());
	    result.setCurrencyCode(new Currency(sourceObject.getCASAAccountSummary().getAccountCurrencyCode()));
	    result.setAccountNumber(sourceObject.getCASAAccountSummary().getAccountNumber());
	    result.setProductCode(ConvertUtils.getProductCode(sourceObject.getCASAAccountSummary().getProduct()));
	    if (sourceObject.getCASAAccountSummary().getAccountAvailableBalance() != null) {
		result.setAvailableBalance(ConvertUtils.convertAmount(sourceObject.getCASAAccountSummary().getAccountAvailableBalance()
			.getAccountCCYBalance()));

	    }
	    if (sourceObject.getCASAAccountSummary().getDomicileBranch() != null) {
		result.setBranchCode(sourceObject.getCASAAccountSummary().getDomicileBranch().getBranchCode());
	    }
	    if (sourceObject.getCASAAccountSummary().getStatementPeriod() != null) {
		result.setStatementStartDate(ConvertUtils.convertDate(sourceObject.getCASAAccountSummary().getStatementPeriod().getStartDate()));
		result.setStatementEndDate(ConvertUtils.convertDate(sourceObject.getCASAAccountSummary().getStatementPeriod().getEndDate()));

	    }
	    // Potential Field Mapping
	    // result.setAccountOpenDate(ConvertUtils.convertDate(sourceObject.getCASAAccountSummary().getAccountOpeningDate()));
	    result.setAccountHolders(sourceObject.getCASAAccountSummary().getAccountTitle());
	}

	if (sourceObject.getCASAAccountSummary() != null && sourceObject.getCASAAccountSummary().getAccountCurrentBalance() != null) {
	    result.setCurrentBalance(ConvertUtils.convertAmount(sourceObject.getCASAAccountSummary().getAccountCurrentBalance()
		    .getAccountCCYBalance()));

	}
	if (sourceObject.getCASAAccountBalance() != null) {
	    result.setOnHoldAmount(ConvertUtils.convertAmount(sourceObject.getCASAAccountBalance().getTotalCASAHoldAmount()));
	    result.setUnclearedFunds(ConvertUtils.convertAmount(sourceObject.getCASAAccountBalance().getTotalUnclearFundAmount()));

	    // NetBalanceAmount
	    if (sourceObject.getCASAAccountBalance().getNetBalanceAmount() != null) {
		result.setNetBalanceAmount(ConvertUtils.convertAmount(sourceObject.getCASAAccountBalance().getNetBalanceAmount()));
	    } else {
		result.setNetBalanceAmount(new BigDecimal(0));
	    }
	    // CurrentBookBalanceAmount
	    if (sourceObject.getCASAAccountBalance().getCurrentBookBalanceAmount() != null) {
		result.setCurrentBookBalanceAmount(ConvertUtils.convertAmount(sourceObject.getCASAAccountBalance().getCurrentBookBalanceAmount()));
	    } else {
		result.setCurrentBookBalanceAmount(new BigDecimal(0));
	    }

	}
	if (sourceObject.getCASAAccountInterest() != null && sourceObject.getCASAAccountInterest().getAverageDailyBalanceInfo() != null) {
	    result.setOverDraftLimit(ConvertUtils.convertAmount(sourceObject.getCASAAccountInterest().getAverageDailyBalanceInfo()
		    .getOverdraftLimitAmount()));
	} else {
	    result.setOverDraftLimit(new BigDecimal(0));
	}
	if (sourceObject.getCASAAccountBalance() != null) {
	    result.setWithdrawalBalance(ConvertUtils.convertAmount(sourceObject.getCASAAccountBalance().getNetAvailableBalanceForWithdrawal()));
	    result.setMinimumBalanceRequired(ConvertUtils.convertAmount(sourceObject.getCASAAccountBalance().getMinimumRequiredBalanceAmount()));
	    result.setEligibleAdvance(ConvertUtils.convertAmount(sourceObject.getCASAAccountBalance().getAdvanceAgainstUnclearedFunds()));
	}

	if (sourceObject.getCASAAccountInterest() != null) {
	    result.setAccuredInterestOfThisYear(ConvertUtils.convertAmount(sourceObject.getCASAAccountInterest()
		    .getInterestAccruedInCurrentFinancialYear()));
	    result.setPreYearAccruedInterest(ConvertUtils.convertAmount(sourceObject.getCASAAccountInterest()
		    .getInterestAccruedInPreviousFinancialYear()));
	}
	if (sourceObject.getCASAAccountControlInfo() != null && sourceObject.getCASAAccountControlInfo().getInternetBankingAccessFlag() != null) {
	    result.setInternetBankingAccessFlag(sourceObject.getCASAAccountControlInfo().getInternetBankingAccessFlag());
	}

	return result;
    }

}
