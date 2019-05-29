package com.barclays.bmg.dao.accountservices.adapter;

import java.util.Calendar;

import com.barclays.bem.FundsTransferHistory.FundsTransferHistory;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.SearchTransactionHistoryServiceRequest;
import com.barclays.bmg.utils.DateTimeUtil;

/**
 * @author BTCI
 *
 * Payload Adapter for Search Transaction History returns BEM DTO
 */
public class SearchTransactionHistoryPayloadAdapter {

	/**
	 * @param workContext
	 * @return FundsTransferHistory
	 */
	public FundsTransferHistory adaptPayload(WorkContext workContext) {

		FundsTransferHistory fundsTransferHistory = new FundsTransferHistory();

		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		SearchTransactionHistoryServiceRequest searchTransactionHistoryServiceRequest = (SearchTransactionHistoryServiceRequest) args[0];
		Context context = searchTransactionHistoryServiceRequest.getContext();
		if(searchTransactionHistoryServiceRequest.isGroupWalletFlow()!=null && searchTransactionHistoryServiceRequest.isGroupWalletFlow().equals("true")){
			fundsTransferHistory.setBillerCode(searchTransactionHistoryServiceRequest.getBillerCode());
			fundsTransferHistory.setDebitAccountNumber(searchTransactionHistoryServiceRequest.getDebitAccountNumber());
			fundsTransferHistory.setFundsTransferType(searchTransactionHistoryServiceRequest.getFundsTransferType());
		}else{
		fundsTransferHistory
				.setFundsTransferType(searchTransactionHistoryServiceRequest
						.getTransactionHistoryDTO().getTransactionType());
		fundsTransferHistory.setCustomerNumber(context.getCustomerId());
		Calendar toTransactionDate = DateTimeUtil
				.getCurrentBusinessCalendar(context);

		Calendar fromTransactionDate = (Calendar) toTransactionDate.clone();
		fromTransactionDate.add(Calendar.MONTH,
				searchTransactionHistoryServiceRequest
						.getTransactionHistoryDTO().getHistoryPeriod());

		/*
		 * Temporarily skipping criteria for transaction date -------------- TO DO
		 */
		// fundsTransferHistory.setFromTransactionDate(fromTransactionDate);
		// fundsTransferHistory.setToTransactionDate(toTransactionDate);
		}
		return fundsTransferHistory;
	}

}
