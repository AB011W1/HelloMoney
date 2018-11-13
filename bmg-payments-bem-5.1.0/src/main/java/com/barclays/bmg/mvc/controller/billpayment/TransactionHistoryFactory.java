package com.barclays.bmg.mvc.controller.billpayment;

import com.barclays.bmg.constants.TransactionHistoryConstants;
import com.barclays.bmg.dto.BillPaymentHistory;
import com.barclays.bmg.dto.TransactionHistoryDTO;

/**
 * @author BTCI
 *
 *         Factory class for TransactionHistoryDTO will return
 *         TransactionHistoryDTO type instance based on Transaction Type
 */
public class TransactionHistoryFactory {

	/**
	 * @param transactionType
	 * @return TransactionHistoryDTO
	 * Factory method will return
	 *         TransactionHistoryDTO type instance based on Transaction Type
	 */
	public TransactionHistoryDTO createTransactionHistory(String transactionType) {
		TransactionHistoryDTO history = null;

		if (TransactionHistoryConstants.BILL_PAYMENT.equals(transactionType)) {
			history = new BillPaymentHistory();
		} /*
		 * else if
		 * (TransactionType.FUND_TRANSFER_INTERNAL.equals(transactionType)) {
		 * history = new InternalFundTransferHistory(); } else if
		 * (TransactionType.FUND_TRANSFER_EXTERNAL.equals(transactionType)) {
		 * history = new DomesticFundTransferHistory(); }
		 */
		return history;
	}

}
