package com.barclays.bmg.service;

import com.barclays.bmg.service.request.TransactionLimitServiceRequest;
import com.barclays.bmg.service.request.UpgradeTransactionLimitServiceRequest;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;
import com.barclays.bmg.service.response.UpgradeTransactionLimitServiceResponse;

public interface TransactionLimitService {

    public TransactionLimitServiceResponse checkTransactionLimit(TransactionLimitServiceRequest request);

    public TransactionLimitServiceResponse getTransactionLimit(TransactionLimitServiceRequest request);

    public UpgradeTransactionLimitServiceResponse upgradeTransactionLimit(UpgradeTransactionLimitServiceRequest request);
}
