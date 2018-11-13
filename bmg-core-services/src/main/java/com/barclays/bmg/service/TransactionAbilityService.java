package com.barclays.bmg.service;

import com.barclays.bmg.service.request.TransactionAbilityRequest;
import com.barclays.bmg.service.response.TransactionAbilityResponse;

public interface TransactionAbilityService {

    public TransactionAbilityResponse checkTransactionAbility(TransactionAbilityRequest request);
}
