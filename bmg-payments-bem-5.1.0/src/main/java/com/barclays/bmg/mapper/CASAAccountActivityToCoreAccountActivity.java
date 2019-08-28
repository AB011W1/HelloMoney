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

import com.barclays.bem.RetrieveCASAAcctTransactionActivity.CASAAccountTransactionList;
import com.barclays.bem.TransactionHistory.TransactionHistory;
import com.barclays.bmg.dto.AccountActivityDTO;
import com.barclays.bmg.dto.AccountActivityListDTO;
import com.barclays.bmg.utils.ConvertUtils;

public class CASAAccountActivityToCoreAccountActivity {

    public AccountActivityListDTO mapBean(CASAAccountTransactionList source, AccountActivityListDTO dest) {
	AccountActivityListDTO result = null;
	if (dest == null) {
	    result = new AccountActivityListDTO();
	} else {
	    result = dest;
	}
	if (source != null) {
	    result.setAccountNumber(source.getAccountNumber());
	    result.setOpeningBalanceAmount(ConvertUtils.convertActivityAmount(source.getOpeningBalanceAmount()));
	    result.setClosingBalanceAmount(ConvertUtils.convertActivityAmount(source.getClosingBalanceAmount()));

	}

	if (source != null && source.getTransactionActivity() != null) {
	    for (TransactionHistory txn : source.getTransactionActivity()) {
		AccountActivityDTO act = new AccountActivityDTO();
		act.setTransactionDate(txn.getTransactionDateTime().getTime());
		// act.setTransactionAmount(ConvertUtils.convertAmount(txn.getTransactionCurrencyAmount()));
		// act.setTransactoinCurrency(txn.getTransactionCurrencyCode());
		// act.setTransactoinReferenceNumber(txn.getTransactionReferenceNumber());

		if (txn.getCheckNumber() == null) {
		    // don't display "0" on view.
		    if ("0".equals(txn.getTransactionReferenceNumber())) {
			act.setTransactoinReferenceNumber("");
		    } else {
			act.setTransactoinReferenceNumber(txn.getTransactionReferenceNumber());
		    }

		} else {
		    // don't display "0" on view.
		    if ("0".equals(txn.getCheckNumber())) {
			act.setTransactoinReferenceNumber("");
		    } else {
			act.setTransactoinReferenceNumber(txn.getCheckNumber());
		    }

		}
		act.setTransactionParticular(ConvertUtils.getTrxDesc(txn));
		if (AccountActivityDTO.CREDIT_FLAG.equals(txn.getCreditDebitTypeCode())) {
		    act.setCreditAmount(ConvertUtils.convertActivityAmount(txn.getSourceAccountCurrencyAmount()));
		} else {
		    act.setDebitAmount(ConvertUtils.convertActivityAmount(txn.getSourceAccountCurrencyAmount()));
		}
		act.setTransactionAmount(ConvertUtils.convertActivityAmount(txn.getSourceAccountCurrencyAmount()));
		act.setBalance(ConvertUtils.convertActivityAmount(txn.getRunningBalanceAmount()));

		result.addAccountActivity(act);
	    }
	}
	return result;
    }

}
