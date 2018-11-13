package com.barclays.bmg.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.barclays.bem.CreditCardTransactionHistory.CreditCardTransactionHistory;
import com.barclays.bem.RetrieveCreditcardAcctTransactionActivity.TransactionActivityInfo;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.dto.AccountActivityDTO;
import com.barclays.bmg.dto.CreditCardActivityDTO;
import com.barclays.bmg.utils.ConvertUtils;

public class CreditCardActivityMapper {

    public List<CreditCardActivityDTO> mapCollection(TransactionActivityInfo[] sourceObject) {

	List<CreditCardActivityDTO> ccActivityDTOList = new ArrayList<CreditCardActivityDTO>();
	if (sourceObject != null) {
	    for (TransactionActivityInfo each : sourceObject) {
		ccActivityDTOList.add(mapBean(each, null));
	    }
	}
	return ccActivityDTOList;

    }

    public List<CreditCardActivityDTO> mapCollectionPrimeVPlus(TransactionActivityInfo[] sourceObject, String paramValue) {

	List<CreditCardActivityDTO> ccActivityDTOList = new ArrayList<CreditCardActivityDTO>();
	if (sourceObject != null) {
	    for (TransactionActivityInfo each : sourceObject) {
		if (StringUtils.isNotBlank(paramValue) && paramValue.equals(SystemParameterConstant.VISIONPLUS_PRIME_VALUE)) {
		    ccActivityDTOList.add(mapBeanVplus(each, null));
		} else {
		    ccActivityDTOList.add(mapBeanPrime(each, null));
		}
	    }
	}
	return ccActivityDTOList;

    }

    // -- Original Method untouched..
    public CreditCardActivityDTO mapBean(TransactionActivityInfo source, CreditCardActivityDTO dest) {
	CreditCardActivityDTO activityDTO = null;
	if (dest == null) {
	    activityDTO = new CreditCardActivityDTO();
	} else {
	    activityDTO = dest;
	}

	if (source != null && source.getCreditCardTransactionHistory() != null && source.getCreditCardTransactionHistory().length == 1) {
	    CreditCardTransactionHistory historyInfo = source.getCreditCardTransactionHistory(0);

	    activityDTO.setCardNumber(historyInfo.getCreditCardNumber());

	    if (historyInfo.getCreditCardNumber() == null) {
		activityDTO.setCardNumber(source.getCreditCardNumber());

	    }
	    activityDTO.setCurrency(historyInfo.getTransactionCurrencyCode());
	    activityDTO.setTransactionDate(ConvertUtils.convertDate(historyInfo.getTransactionDateTime()));
	    // result.setTransactoinReferenceNumber(source.getCreditCardTxnReferenceNumber());

	    activityDTO.setTransactionParticular(ConvertUtils.getTrxDesc(historyInfo));
	    // True false creditDebit Flag;
	    // result.setCreditDebitFlag(source.getCreditDebitFlag());
	    if (AccountActivityDTO.CREDIT_FLAG.equals(historyInfo.getCreditDebitTypeCode())) {
		activityDTO.setCreditDebitFlag(AccountActivityDTO.CREDIT_FLAG);
		activityDTO.setCreditSuffix(AccountActivityDTO.CREDIT_SUFFIX);
		activityDTO.setCreditAmount(ConvertUtils.convertPositiveAmount(historyInfo.getTransactionCurrencyAmount()));
	    } else {
		activityDTO.setCreditDebitFlag(AccountActivityDTO.DEBIT_FLAG);
		activityDTO.setDebitAmount(ConvertUtils.convertPositiveAmount(historyInfo.getTransactionCurrencyAmount()));
	    }
	    activityDTO.setTransactionAmount(ConvertUtils.convertPositiveAmount(historyInfo.getTransactionCurrencyAmount()));
	    activityDTO.setTransactionPostDate(ConvertUtils.convertDate(historyInfo.getPostingDate()));

	}
	return activityDTO;
    }

    // -- To support VPLUS
    public CreditCardActivityDTO mapBeanVplus(TransactionActivityInfo source, CreditCardActivityDTO dest) {
	CreditCardActivityDTO activityDTO = null;
	if (dest == null) {
	    activityDTO = new CreditCardActivityDTO();
	} else {
	    activityDTO = dest;
	}

	if (source != null && source.getCreditCardTransactionHistory() != null && source.getCreditCardTransactionHistory().length == 1) {
	    CreditCardTransactionHistory historyInfo = source.getCreditCardTransactionHistory(0);

	    activityDTO.setCardNumber(historyInfo.getCreditCardNumber());

	    if (historyInfo.getCreditCardNumber() == null) {
		activityDTO.setCardNumber(source.getCreditCardNumber());

	    }

	    // #1528 - EMK; This is not require; local currency is displayed
	    // when no currency for txn.
	    // activityDTO.setCurrency(historyInfo.getTransactionCurrencyCode());

	    activityDTO.setTransactionDate(ConvertUtils.convertDate(historyInfo.getEffectiveDate()));

	    // result.setTransactoinReferenceNumber(source.getCreditCardTxnReferenceNumber());

	    activityDTO.setTransactionParticular(ConvertUtils.getTrxDesc(historyInfo));
	    // True false creditDebit Flag;
	    // result.setCreditDebitFlag(source.getCreditDebitFlag());
	    if ("C".equalsIgnoreCase(historyInfo.getCreditDebitTypeCode())) { // AccountActivityDTO.CREDIT_FLAG
		activityDTO.setCreditDebitFlag(AccountActivityDTO.CREDIT_FLAG);
		activityDTO.setCreditSuffix(AccountActivityDTO.CREDIT_SUFFIX);
		activityDTO.setCreditAmount(ConvertUtils.convertPositiveAmount(historyInfo.getLCYAmount()));
	    } else {
		activityDTO.setCreditDebitFlag(AccountActivityDTO.DEBIT_FLAG);
		activityDTO.setDebitAmount(ConvertUtils.convertPositiveAmount(historyInfo.getLCYAmount()));
	    }
	    activityDTO.setTransactionAmount(ConvertUtils.convertPositiveAmount(historyInfo.getLCYAmount()));
	    activityDTO.setTransactionPostDate(ConvertUtils.convertDate(historyInfo.getPostingDate()));

	}
	return activityDTO;
    }

    // -- To support PRIME
    public CreditCardActivityDTO mapBeanPrime(TransactionActivityInfo source, CreditCardActivityDTO dest) {
	CreditCardActivityDTO activityDTO = null;
	if (dest == null) {
	    activityDTO = new CreditCardActivityDTO();
	} else {
	    activityDTO = dest;
	}

	if (source != null && source.getCreditCardTransactionHistory() != null && source.getCreditCardTransactionHistory().length == 1) {
	    CreditCardTransactionHistory historyInfo = source.getCreditCardTransactionHistory(0);

	    activityDTO.setCardNumber(historyInfo.getCreditCardNumber());

	    if (historyInfo.getCreditCardNumber() == null) {
		activityDTO.setCardNumber(source.getCreditCardNumber());

	    }
	    activityDTO.setCurrency(historyInfo.getTransactionCurrencyCode());
	    activityDTO.setTransactionDate(ConvertUtils.convertDate(historyInfo.getTransactionDateTime()));
	    // result.setTransactoinReferenceNumber(source.getCreditCardTxnReferenceNumber());

	    activityDTO.setTransactionParticular(ConvertUtils.getTrxDesc(historyInfo));
	    // True false creditDebit Flag;
	    // result.setCreditDebitFlag(source.getCreditDebitFlag());
	    if (AccountActivityDTO.CREDIT_FLAG.equals(historyInfo.getCreditDebitTypeCode())) {
		activityDTO.setCreditDebitFlag(AccountActivityDTO.CREDIT_FLAG);
		activityDTO.setCreditSuffix(AccountActivityDTO.CREDIT_SUFFIX);
		activityDTO.setCreditAmount(ConvertUtils.convertPositiveAmount(historyInfo.getTransactionCurrencyAmount()));
	    } else {
		activityDTO.setCreditDebitFlag(AccountActivityDTO.DEBIT_FLAG);
		activityDTO.setDebitAmount(ConvertUtils.convertPositiveAmount(historyInfo.getTransactionCurrencyAmount()));
	    }
	    activityDTO.setTransactionAmount(ConvertUtils.convertPositiveAmount(historyInfo.getTransactionCurrencyAmount()));
	    activityDTO.setTransactionPostDate(ConvertUtils.convertDate(historyInfo.getPostingDate()));

	}
	return activityDTO;
    }

}
