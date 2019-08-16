package com.barclays.bmg.operation.accountdetails;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardActivityDTO;
import com.barclays.bmg.dto.CreditCardDTO;
import com.barclays.bmg.dto.CreditCardTransactionHistoryDTO;
import com.barclays.bmg.operation.BMBCommonOperation;

/**
 * @author BMB Team
 *
 */
public abstract class AbstractCreditCardOperation extends BMBCommonOperation {

    private String PRIMARY_CARD = "PRI";
    private String REPLACEMENT_CARD = "REP";
    private String SUPPLIMENTARY_CARD = "SUP";

    /**
     * returns the Primary Card ransaction history
     *
     * @param detailAccountDTO
     * @param activityList
     * @return
     */
    protected CreditCardTransactionHistoryDTO getPrimaryCardHistoryList(CreditCardAccountDTO detailAccountDTO,
	    List<CreditCardActivityDTO> activityList) {
	// set Primary Card recent Trans
	CreditCardTransactionHistoryDTO primaryCardHistory = new CreditCardTransactionHistoryDTO();
	if (detailAccountDTO != null && detailAccountDTO.getPrimary() != null) {
	    primaryCardHistory.setCardNumber(detailAccountDTO.getPrimary().getCardNumber());

	    primaryCardHistory.setReplacementCardNumberList(getReplacementCardNumberList(detailAccountDTO, activityList));

	    primaryCardHistory.setCardHolder(detailAccountDTO.getPrimary().getCardHolder());
	    primaryCardHistory.setCreditCardActivityList(getActivityByCardNumber(detailAccountDTO, primaryCardHistory.getCardNumber(), activityList,
		    true));
	}

	primaryCardHistory.setCardType(PRIMARY_CARD);
	return primaryCardHistory;
    }

    /**
     * returns the Primary Card ransaction history
     *
     * @param detailAccountDTO
     * @param activityList
     * @return
     */
    protected CreditCardTransactionHistoryDTO getPrimaryCardHistoryListWithoutActivityList(CreditCardAccountDTO detailAccountDTO) {
	// set Primary Card recent Trans
	CreditCardTransactionHistoryDTO primaryCardHistory = new CreditCardTransactionHistoryDTO();

	if (detailAccountDTO != null && detailAccountDTO.getPrimary() != null) {
	    primaryCardHistory.setCardNumber(detailAccountDTO.getPrimary().getCardNumber());

	    primaryCardHistory.setCardHolder(detailAccountDTO.getPrimary().getCardHolder());
	}

	primaryCardHistory.setCardType(PRIMARY_CARD);
	return primaryCardHistory;
    }

    /**
     * Returns Supplementary card transaction history
     *
     * @param detailAccountDTO
     * @param activityList
     * @return
     */
    protected List<CreditCardTransactionHistoryDTO> getSupplimentryCardGroupedHistoryList(CreditCardAccountDTO detailAccountDTO,
	    List<CreditCardActivityDTO> activityList) {
	List<CreditCardTransactionHistoryDTO> returnHistoryList = new ArrayList<CreditCardTransactionHistoryDTO>();
	if (activityList != null) {
	    // Set supplementary card recent Trans.
	    List<CreditCardDTO> suppleCardDTOList = detailAccountDTO.getSupplementaries();
	    if (suppleCardDTOList != null) {
		for (CreditCardDTO creditCardDTO : suppleCardDTOList) {
		    CreditCardTransactionHistoryDTO suppleCardHistory = new CreditCardTransactionHistoryDTO();
		    suppleCardHistory.setCardNumber(creditCardDTO.getCardNumber());
		    // Changed to set the card holder name from PRI Card
		    suppleCardHistory.setCardHolder(creditCardDTO.getCardHolder());
		    // suppleCardHistory.setCardHolder(detailAccountDTO.getPrimary().getCardHolder());

		    suppleCardHistory.setReplacementCardNumberList(getReplacementCardNumberList(detailAccountDTO, activityList));
		    suppleCardHistory.setCreditCardActivityList(getActivityByCardNumber(detailAccountDTO, suppleCardHistory.getCardNumber(),
			    activityList, false));
		    suppleCardHistory.setCardType(SUPPLIMENTARY_CARD);
		    returnHistoryList.add(suppleCardHistory);
		}

	    }

	}

	return returnHistoryList;
    }

    /**
     * returns the Replaced card transaction history
     *
     * @param detailAccountDTO
     * @param activityList
     * @return
     */
    protected List<CreditCardTransactionHistoryDTO> getReplacementCardGroupedHistoryList(CreditCardAccountDTO detailAccountDTO,
	    List<CreditCardActivityDTO> activityList) {
	List<CreditCardTransactionHistoryDTO> returnHistoryList = new ArrayList<CreditCardTransactionHistoryDTO>();
	if (activityList != null) {

	    // Sort the credit card activities by descending first.
	    // Collections.sort(activityList, new
	    // OrderList<CreditCardActivityDTO>());
	    // Reverse the list according to the system parameter configuration.
	    // if(!configUtility.getSystemParameterBooleanValueById(AccountConstants.CREDITCARD_TXN_DATE_DESCENDING_INDICATOR,
	    // ActivityIdConstantBean.CREDIT_DETAIL_ACTIVITY_ID)) {
	    // Collections.reverse(activityList);
	    // }

	    // Collections.reverse(activityList);

	    // handling replacement card
	    List<String> replacementCardNumberList = getReplacementCardNumberList(detailAccountDTO, activityList);
	    for (String replacementCardNumber : replacementCardNumberList) {
		CreditCardTransactionHistoryDTO replacementCardHistory = new CreditCardTransactionHistoryDTO();
		replacementCardHistory.setCardNumber(replacementCardNumber);
		// Added to set the card holder name from PRI Card
		replacementCardHistory.setCardHolder(detailAccountDTO.getPrimary().getCardHolder());

		replacementCardHistory.setReplacementCardNumberList(replacementCardNumberList);
		replacementCardHistory.setCreditCardActivityList(getReplacementCardActivityByCardNumber(replacementCardNumber, activityList));
		replacementCardHistory.setCardType(REPLACEMENT_CARD);
		returnHistoryList.add(replacementCardHistory);
	    }
	}

	return returnHistoryList;
    }

    /**
     * returns the replacement card numbers
     *
     * @param detailAccountDTO
     * @param activityList
     * @return
     */
    private List<String> getReplacementCardNumberList(CreditCardAccountDTO detailAccountDTO, List<CreditCardActivityDTO> activityList) {
	List<String> replacementCardNumberList = new ArrayList<String>();
	if (activityList != null) {
	    for (CreditCardActivityDTO dto : activityList) {
		if (dto.getCardNumber() != null && isReplacementCard(dto.getCardNumber(), detailAccountDTO)) {
		    if (!replacementCardNumberList.contains(dto.getCardNumber())) {
			replacementCardNumberList.add(dto.getCardNumber());
		    }
		}
	    }
	}
	return replacementCardNumberList;
    }

    /**
     * returns credit card account activity for a card number
     *
     * @param creditCardAccount
     * @param cardNumber
     * @param sourceDTOList
     * @param primaryFlag
     * @return
     */
    private List<CreditCardActivityDTO> getActivityByCardNumber(CreditCardAccountDTO creditCardAccount, String cardNumber,
	    List<CreditCardActivityDTO> sourceDTOList, boolean primaryFlag) {
	List<CreditCardActivityDTO> returnActivityList = new ArrayList<CreditCardActivityDTO>();

	int sequence = 1;
	for (int i = 0; i < sourceDTOList.size(); i++) {
	    CreditCardActivityDTO activityDTO = sourceDTOList.get(i);
	    if (primaryFlag) {
		if (cardNumber.equals(activityDTO.getCardNumber()) || creditCardAccount.getAccountNumber().equals(activityDTO.getCardNumber())) {
		    activityDTO.setSequenceNumber(sequence++);
		    returnActivityList.add(activityDTO);
		}
	    } else {
		if (cardNumber.equals(activityDTO.getCardNumber())) {
		    activityDTO.setSequenceNumber(sequence++);
		    returnActivityList.add(activityDTO);
		}
	    }

	}
	return returnActivityList;
    }

    /*
     * private List<CreditCardActivityDTO> getRecentTransactionForCrediCard(int recentTrxNumber, List<CreditCardActivityDTO> sourceActivityList) {
     * List<CreditCardActivityDTO> returnList = new ArrayList<CreditCardActivityDTO>(); if (sourceActivityList != null) {
     *
     * int sourceListSize = sourceActivityList.size();
     *
     * if (sourceListSize > recentTrxNumber) { for (int i = 0; i < recentTrxNumber; i++) {
     *
     * returnList.add(sourceActivityList.get(i));
     *
     * }
     *
     * } else {
     *
     * returnList = sourceActivityList;
     *
     * } }
     *
     * return returnList; }
     */

    /**
     * returns the replacement card activity list
     *
     * @param replacementCardNumber
     * @param activityList
     * @return
     */
    private List<CreditCardActivityDTO> getReplacementCardActivityByCardNumber(String replacementCardNumber, List<CreditCardActivityDTO> activityList) {
	List<CreditCardActivityDTO> returnActivityList = new ArrayList<CreditCardActivityDTO>();
	int sequence = 1;
	for (int i = 0; i < activityList.size(); i++) {
	    CreditCardActivityDTO activityDTO = activityList.get(i);
	    if (replacementCardNumber.equals(activityDTO.getCardNumber())) {
		activityDTO.setSequenceNumber(sequence++);
		returnActivityList.add(activityDTO);
	    }
	}
	return returnActivityList;
    }

    /**
     * Check whether the card is replacement card
     *
     * @param cardNumber
     * @param detailAccountDTO
     * @return
     */
    private boolean isReplacementCard(String cardNumber, CreditCardAccountDTO detailAccountDTO) {
	// TODO Auto-generated method stub
	boolean primaryFlag = false;
	boolean supplementaryFlag = true;
	boolean accountNumberFlag = false;
	if (cardNumber == null || "".equals(cardNumber)) {
	    return false;
	}
	String primaryCardNumber = detailAccountDTO.getPrimary().getCardNumber();
	String accountNumber = detailAccountDTO.getAccountNumber();
	List<CreditCardDTO> suppleCardDTOList = detailAccountDTO.getSupplementaries();
	if (!cardNumber.equals(primaryCardNumber)) {
	    primaryFlag = true;
	}
	if (!cardNumber.equals(accountNumber)) {
	    accountNumberFlag = true;
	}
	if (suppleCardDTOList != null) {
	    for (CreditCardDTO creditCardDTO : suppleCardDTOList) {
		if (cardNumber.equals(creditCardDTO.getCardNumber())) {
		    supplementaryFlag = false;
		    break;
		}
	    }
	}
	return primaryFlag && supplementaryFlag && accountNumberFlag;
    }

    /*
     * private List<CreditCardActivityDTO> getActivityListByCardNumber( List<CreditCardTransactionHistoryDTO> activityDTOList, String cardNumber) {
     * List<CreditCardActivityDTO> returnActivityList = null; if (activityDTOList != null) { for (CreditCardTransactionHistoryDTO activityDTO :
     * activityDTOList) { if (cardNumber.equals(activityDTO.getCardNumber())) { returnActivityList = activityDTO.getCreditCardActivityList(); break; }
     * }
     *
     * } return returnActivityList; }
     */

    /**
     * get the credit card transaction history list for a card number
     *
     * @param cardNo
     * @param primaryCardHistory
     * @param replacementCardGroupedHistoryList
     * @param supplimentryCardGroupedHistoryList
     * @return
     */
    protected CreditCardTransactionHistoryDTO getSelectedCardHistory(String cardNo, CreditCardTransactionHistoryDTO primaryCardHistory,
	    List<CreditCardTransactionHistoryDTO> replacementCardGroupedHistoryList,
	    List<CreditCardTransactionHistoryDTO> supplimentryCardGroupedHistoryList, boolean is_unbilled) {

	CreditCardTransactionHistoryDTO returnCCTrnxHist = null; // new
	// CreditCardTransactionHistoryDTO();

	if (primaryCardHistory != null && cardNo.equals(primaryCardHistory.getCardNumber())) {
	    returnCCTrnxHist = primaryCardHistory;
	    return returnCCTrnxHist;
	}

	if (replacementCardGroupedHistoryList != null) {
	    for (CreditCardTransactionHistoryDTO eachCCTrxHist : replacementCardGroupedHistoryList) {

		// Commented to fix for defect# 568 - card doesn't not exist
		// issue as previously it is overwriting primary card with
		// replaced card
		if (is_unbilled) {
		    if (eachCCTrxHist != null) {
			if (cardNo.equals(eachCCTrxHist.getCardNumber())) {
			    returnCCTrnxHist = eachCCTrxHist;
			    break;
			}
		    }
		} else {
		    returnCCTrnxHist = eachCCTrxHist;
		    break;
		}
	    }
	}

	if (supplimentryCardGroupedHistoryList != null) {
	    for (CreditCardTransactionHistoryDTO eachCCTrxHist : supplimentryCardGroupedHistoryList) {
		if (cardNo.equals(eachCCTrxHist.getCardNumber())) {
		    returnCCTrnxHist = eachCCTrxHist;
		    break;
		}
	    }
	}

	return returnCCTrnxHist;
    }
}
