package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.TransactionAbilityRequest;
import com.barclays.bmg.service.response.TransactionAbilityResponse;

public interface TransactionCutOffDAO {

    public TransactionAbilityResponse getTransactionCutOffTime(TransactionAbilityRequest request);
}
