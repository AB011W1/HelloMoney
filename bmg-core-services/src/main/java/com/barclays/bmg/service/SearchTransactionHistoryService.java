package com.barclays.bmg.service;

import com.barclays.bmg.service.request.SearchTransactionHistoryServiceRequest;
import com.barclays.bmg.service.response.SearchTransactionHistoryServiceResponse;

public interface SearchTransactionHistoryService {

    SearchTransactionHistoryServiceResponse searchTransactionHistory(SearchTransactionHistoryServiceRequest searchTransactionHistoryServiceRequest);

}
