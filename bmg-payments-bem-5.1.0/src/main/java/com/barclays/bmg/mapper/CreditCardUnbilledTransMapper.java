package com.barclays.bmg.mapper;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.CreditCardTransactionHistory.CreditCardTransactionHistory;
import com.barclays.bem.RetrieveCreditCardUnbilledTransactions.TransactionActivityInfo;
import com.barclays.bmg.dto.AccountActivityDTO;
import com.barclays.bmg.dto.CreditCardActivityDTO;
import com.barclays.bmg.utils.ConvertUtils;

public class CreditCardUnbilledTransMapper {

    public List<CreditCardActivityDTO> mapCollection(
    // Original mapCollection; UAE/SSA

	    TransactionActivityInfo[] transactionActivityInfos) {

	List<CreditCardActivityDTO> ccActivityDTOList = new ArrayList<CreditCardActivityDTO>();
	if (transactionActivityInfos != null) {
	    for (TransactionActivityInfo each : transactionActivityInfos) {
		ccActivityDTOList.add(mapBean(each, null));
	    }
	}

	return ccActivityDTOList;

    }

    // PRIME-VPLUS-MERGE
    public List<CreditCardActivityDTO> mapCollectionPrimeVPlus(
    // Made mapCollectionPrimeVPlus
	    TransactionActivityInfo[] transactionActivityInfos) {

	List<CreditCardActivityDTO> ccActivityDTOList = new ArrayList<CreditCardActivityDTO>();
	if (transactionActivityInfos != null) {
	    for (TransactionActivityInfo each : transactionActivityInfos) {

		ccActivityDTOList.add(mapBeanVPlus(each, null));
	    }
	}

	return ccActivityDTOList;

    }

    // PRIME-VPLUS-MERGE
    public CreditCardActivityDTO mapBeanPrime(TransactionActivityInfo source, CreditCardActivityDTO dest) {
	CreditCardActivityDTO activityDTO = null;
	if (dest == null) {
	    activityDTO = new CreditCardActivityDTO();
	} else {
	    activityDTO = dest;
	}

	if (source != null && source.getCreditCardTransactions() != null && source.getCreditCardTransactions().length == 1) {
	    CreditCardTransactionHistory historyInfo = source.getCreditCardTransactions(0);

	    activityDTO.setCardNumber(source.getCreditCardNumber());
	    activityDTO.setCurrency(historyInfo.getTransactionCurrencyCode());
	    activityDTO.setTransactionDate(ConvertUtils.convertDate(historyInfo.getTransactionDateTime()));

	    activityDTO.setTransactionParticular(ConvertUtils.getTrxDesc(historyInfo));

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

    // PRIME-VPLUS-MERGE
    public CreditCardActivityDTO mapBeanVPlus(TransactionActivityInfo source, CreditCardActivityDTO dest) {
	CreditCardActivityDTO activityDTO = null;
	if (dest == null) {
	    activityDTO = new CreditCardActivityDTO();
	} else {
	    activityDTO = dest;
	}

	if (source != null && source.getCreditCardTransactions() != null && source.getCreditCardTransactions().length == 1) {
	    CreditCardTransactionHistory historyInfo = source.getCreditCardTransactions(0);

	    activityDTO.setCardNumber(source.getCreditCardNumber());

	    activityDTO.setTransactionDate(ConvertUtils.convertDate(historyInfo.getEffectiveDate())); // Orchard changes

	    activityDTO.setTransactionParticular(ConvertUtils.getTrxDesc(historyInfo));

	    if ("C".equalsIgnoreCase(historyInfo.getCreditDebitTypeCode())) {
		activityDTO.setCreditDebitFlag(AccountActivityDTO.CREDIT_FLAG);
		activityDTO.setCreditSuffix(AccountActivityDTO.CREDIT_SUFFIX);
		activityDTO.setCreditAmount(ConvertUtils.convertPositiveAmount(historyInfo.getLCYAmount()));
	    } else {
		activityDTO.setCreditDebitFlag(AccountActivityDTO.DEBIT_FLAG);
		activityDTO.setDebitAmount(ConvertUtils.convertPositiveAmount(historyInfo.getLCYAmount()));
	    }
	    activityDTO.setTransactionAmount(ConvertUtils.convertPositiveAmount(historyInfo.getLCYAmount()));
	    activityDTO.setTransactionPostDate(ConvertUtils.convertDate(historyInfo.getPostingDate()));
	    activityDTO.setMerchantName(historyInfo.getMerchantInfo().getMerchantName());

	}

	return activityDTO;
    }

    public CreditCardActivityDTO mapBean(TransactionActivityInfo source, CreditCardActivityDTO dest) {
	CreditCardActivityDTO activityDTO = null;
	if (dest == null) {
	    activityDTO = new CreditCardActivityDTO();
	} else {
	    activityDTO = dest;
	}
	// result.setCardNumber(source.getCreditCardNumber());
	// if (source.getEmbossedNameOnCard() != null &&
	// source.getEmbossedNameOnCard().length > 0) {
	// result.setCardHolder(source.getEmbossedNameOnCard(0));
	// }
	if (source != null && source.getCreditCardTransactions() != null && source.getCreditCardTransactions().length == 1) {
	    CreditCardTransactionHistory historyInfo = source.getCreditCardTransactions(0);

	    // activityDTO.setCardNumber(historyInfo.getCreditCardNumber());
	    activityDTO.setCardNumber(source.getCreditCardNumber());
	    activityDTO.setCurrency(historyInfo.getTransactionCurrencyCode());
	    activityDTO.setTransactionDate(ConvertUtils.convertDate(historyInfo.getTransactionDateTime()));
	    // result.setTransactoinReferenceNumber(source.getCreditCardTxnReferenceNumber());
	    // activityDTO.setSequenceNumber(i++);
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
