package com.barclays.bmg.cashsend.dao;

import com.barclays.bmg.cashsend.service.request.CashSendOneTimeExecuteServiceRequest;
import com.barclays.bmg.cashsend.service.response.CashSendOneTimeExecuteServiceResponse;

public interface CashSendEncryptionDAO {

    public CashSendOneTimeExecuteServiceResponse encryptPin(CashSendOneTimeExecuteServiceRequest request);
}
