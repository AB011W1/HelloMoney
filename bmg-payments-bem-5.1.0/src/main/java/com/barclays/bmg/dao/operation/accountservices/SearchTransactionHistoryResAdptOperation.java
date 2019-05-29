package com.barclays.bmg.dao.operation.accountservices;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.FundsTransferSummary.FundsTransferSummary;
import com.barclays.bem.SearchFundsTransferHistory.SearchFundsTransferHistoryResponse;
import com.barclays.bmg.constants.TransactionHistoryConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BillPaymentHistory;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.TransactionHistoryDTO;
import com.barclays.bmg.service.request.SearchTransactionHistoryServiceRequest;
import com.barclays.bmg.service.response.SearchTransactionHistoryServiceResponse;

/**
 * @author BTCI
 *
 *         Response adapter class for Search Transaction History BEM response (SearchFundsTransferHistoryResponse)
 *
 */
public class SearchTransactionHistoryResAdptOperation extends AbstractResAdptOperationAcct {

    /**
     * Adapt response.
     *
     * @param workContext
     *            the work context
     * @param obj
     *            the obj
     * @return SearchTransactionHistoryServiceResponse
     */
    public SearchTransactionHistoryServiceResponse adaptResponse(WorkContext workContext, Object obj) {

	SearchTransactionHistoryServiceResponse response = new SearchTransactionHistoryServiceResponse();

	SearchFundsTransferHistoryResponse bemResponse = (SearchFundsTransferHistoryResponse) obj;
	List<TransactionHistoryDTO> transactionHistoryDTOList = new ArrayList<TransactionHistoryDTO>();

	if (checkResponseHeader(bemResponse.getResponseHeader())) {
	    response.setSuccess(true);
	} else {
	    response.setSuccess(false);
	}

	if (null != bemResponse.getFundTransferHistoryList()) {
	    FundsTransferSummary[] result = bemResponse.getFundTransferHistoryList().getFundsTransferSummary();
		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		SearchTransactionHistoryServiceRequest searchTransactionHistoryServiceRequest = (SearchTransactionHistoryServiceRequest) args[0];
		String groupwalletFlow=searchTransactionHistoryServiceRequest.isGroupWalletFlow();
	    if (result != null && result.length > 0) {
		for (FundsTransferSummary fundsTransferSummary : result) {

		    String transactionType = fundsTransferSummary.getFundsTransferType();

		    if (TransactionHistoryConstants.BILL_PAYMENT.equals(transactionType)||(groupwalletFlow!=null && groupwalletFlow.equals("true"))) {
			BillPaymentHistory history = new BillPaymentHistory();
			// populate DTO for Bill payment when txn history is of
			// BP type
			history = populateBillPayment(history, fundsTransferSummary);
			transactionHistoryDTOList.add(history);
		    }

		}
	    }
	}

	response.setTransactionHistoryDTOList(transactionHistoryDTOList);

	return response;
    }

    /**
     * @param BillPaymentHistory
     * @param FundsTransferSummary
     * @return BillPaymentHistory populate DTO for Bill payment when txn history is of BP type
     */
    private BillPaymentHistory populateBillPayment(BillPaymentHistory history, FundsTransferSummary source) {
	CustomerAccountDTO fromAccount = new CustomerAccountDTO();
	fromAccount.setAccountNumber(source.getDebitAccountNumber());
	history.setFromAccount(fromAccount);
	history.setBillerId(source.getBillerCode());
	history.setBillerName(source.getBillerName());

	history.setBillReferenceNumber(source.getBillerReferenceNumber());
	if (null != source.getCreditAmount() && null != source.getCreditAccountCurrencyCode()) {
	    Amount amount = new Amount(source.getCreditAccountCurrencyCode(), BigDecimal.valueOf(source.getCreditAmount()));
	    history.setAmount(amount);
	}
	history.setTransactionDate(source.getTransactionDateTime().getTime());
	history.setTransactionReferenceNumber(source.getTrxReferenceNumber());
	history.setStatus(source.getTransactionStatusCode());
	history.setTransactionType(source.getFundsTransferType());
	return history;
    }

}
