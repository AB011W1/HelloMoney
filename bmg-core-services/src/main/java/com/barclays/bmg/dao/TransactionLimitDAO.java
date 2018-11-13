package com.barclays.bmg.dao;

import java.math.BigDecimal;

import com.barclays.bmg.service.request.TransactionLimitServiceRequest;
import com.barclays.bmg.service.request.UpgradeTransactionLimitServiceRequest;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;
import com.barclays.bmg.service.response.UpgradeTransactionLimitServiceResponse;

public interface TransactionLimitDAO {

    public TransactionLimitServiceResponse getTransactionLimit(TransactionLimitServiceRequest request);

    public UpgradeTransactionLimitServiceResponse upgradeTransactionLimit(UpgradeTransactionLimitServiceRequest request);

    public BigDecimal getTransactionAvlblDailyLimitForUB(TransactionLimitServiceRequest request, BigDecimal dailyAmount);

    public UpgradeTransactionLimitServiceResponse upgradeTransactionLimitForUB(UpgradeTransactionLimitServiceRequest request);

    public Integer getTransactionCountForUB(UpgradeTransactionLimitServiceRequest request);

}
