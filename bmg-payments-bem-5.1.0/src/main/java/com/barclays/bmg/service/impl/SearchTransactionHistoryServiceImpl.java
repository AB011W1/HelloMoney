package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.SearchTransactionHistoryDAO;
import com.barclays.bmg.service.SearchTransactionHistoryService;
import com.barclays.bmg.service.request.SearchTransactionHistoryServiceRequest;
import com.barclays.bmg.service.response.SearchTransactionHistoryServiceResponse;

/**
 * @author BTCI
 *
 */
public class SearchTransactionHistoryServiceImpl implements
		SearchTransactionHistoryService {

	private SearchTransactionHistoryDAO searchTransactionHistoryDAO;

	/**
	 * @param searchTransactionHistoryDAO
	 */
	public void setSearchTransactionHistoryDAO(
			SearchTransactionHistoryDAO searchTransactionHistoryDAO) {
		this.searchTransactionHistoryDAO = searchTransactionHistoryDAO;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.barclays.bmg.service.SearchTransactionHistoryService#
	 * searchTransactionHistory
	 * (com.barclays.bmg.service.request.SearchTransactionHistoryServiceRequest)
	 */
	@Override
	public SearchTransactionHistoryServiceResponse searchTransactionHistory(
			SearchTransactionHistoryServiceRequest searchTransactionHistoryServiceRequest) {
		SearchTransactionHistoryServiceResponse searchTransactionHistoryServiceResponse = searchTransactionHistoryDAO
				.searchTransactionHistory(searchTransactionHistoryServiceRequest);
		return searchTransactionHistoryServiceResponse;
	}

}
